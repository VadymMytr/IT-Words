package ua.vadymmy.it.words.data.server

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import ua.vadymmy.it.words.data.server.mappers.complaintsHashMap
import ua.vadymmy.it.words.data.server.mappers.learningHashMap
import ua.vadymmy.it.words.data.server.mappers.mapToHashMap
import ua.vadymmy.it.words.data.server.mappers.mapToWord
import ua.vadymmy.it.words.data.server.mappers.mapToWordKit
import ua.vadymmy.it.words.data.server.mappers.wordProgress
import ua.vadymmy.it.words.data.server.mappers.wordsUUIDs
import ua.vadymmy.it.words.data.server.mappers.wordsUUIDsHashMap
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordProgress
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.utils.AuthHelper
import ua.vadymmy.it.words.utils.resume
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Reusable
class FirebaseServerRepository @Inject constructor(
    private val authHelper: AuthHelper
) : ServerRepository {

    private val db get() = Firebase.firestore
    private val users get() = db.collection(USERS_COLLECTION)
    private val words get() = db.collection(WORDS_COLLECTION)
    private val predefinedKits get() = db.collection(WORDS_KITS_COLLECTION)

    private val currentUserDoc get() = users.document(authHelper.currentUser.uid)
    private val learningWords get() = currentUserDoc.collection(LEARNING_WORDS_COLLECTION)
    private val learningKits get() = currentUserDoc.collection(LEARNING_KITS_COLLECTION)

    override suspend fun loginUser(user: User) = suspendCoroutine<Unit> { continuation ->
        currentUserDoc.get().addOnSuccessListener {
            if (it.exists()) {
                continuation.resume()
                return@addOnSuccessListener
            }

            currentUserDoc.set(user.mapToHashMap()).addOnCompleteListener {
                continuation.resume()
            }
        }.addOnFailureListener {
            it.printStackTrace()
            continuation.resume()
        }
    }

    override suspend fun updateUser(user: User) = suspendCoroutine<Unit> { continuation ->
        currentUserDoc.update(user.mapToHashMap()).addCompleteListener {
            continuation.resume()
        }
    }

    override suspend fun addWord(word: Word) = suspendCoroutine<Unit> { continuation ->
        words.document(word.uuid).set(word.mapToHashMap()).addCompleteListener {
            continuation.resume()
        }
    }

    override suspend fun updateWordProgress(word: Word) = suspendCoroutine<Unit> { continuation ->
        learningWords.document(word.uuid).set(word.learningHashMap).addCompleteListener {
            continuation.resume()
        }
    }

    override suspend fun addComplaintOn(word: Word) = suspendCoroutine<Unit> { continuation ->
        words.document(word.uuid).set(word.complaintsHashMap).addCompleteListener {
            continuation.resume()
        }
    }

    override suspend fun addLearningWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            learningKits.document(wordKit.uuid).set(wordKit.mapToHashMap()).addCompleteListener {
                continuation.resume()
            }
        }

    override suspend fun getLearningWordKits(): List<WordKit> = getWordsKits(learningKits)

    override suspend fun addPredefinedWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            predefinedKits.document(wordKit.uuid).set(wordKit.mapToHashMap()).addCompleteListener {
                continuation.resume()
            }
        }

    override suspend fun getPredefinedKits(): List<WordKit> = getWordsKits(
        predefinedKits,
        this::isPredefinedKitNotLearning
    )

    override suspend fun removeLearningWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            learningKits.document(wordKit.uuid).delete().addCompleteListener {
                continuation.resume()
            }
        }

    override suspend fun updateLearningWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            learningKits.document(wordKit.uuid).set(wordKit.wordsUUIDsHashMap).addCompleteListener {
                continuation.resume()
            }
        }

    private suspend fun getWordsKits(
        collection: CollectionReference,
        isKitRequired: suspend (uuid: String) -> Boolean = { _ -> true }
    ): List<WordKit> {
        val kitsDeferred = CompletableDeferred<List<WordKit>>()
        val kits = mutableListOf<WordKit>()

        collection.get().addOnSuccessListener { query ->
            runBlocking {
                query.forEach { doc ->
                    if (isKitRequired(doc.id)) {
                        val words = getWords(doc.wordsUUIDs)
                        kits.add(doc.mapToWordKit(words))
                    }
                }

                kitsDeferred.complete(kits)
            }
        }.addOnFailureListener {
            it.printStackTrace()
            kitsDeferred.complete(listOf())
        }

        return kitsDeferred.await()
    }

    private suspend fun isPredefinedKitNotLearning(uuid: String) =
        suspendCoroutine<Boolean> { continuation ->
            learningKits.document(uuid).get().addCompleteListener {
                continuation.resume(!it.exists())
            }
        }

    private suspend fun getWords(uuids: List<String>): List<Word> = uuids.mapNotNull { getWord(it) }

    private suspend fun getWord(uuid: String): Word? = findWord(uuid)?.apply {
        progress = findWordProgress(uuid)
    }

    private suspend fun findWord(uuid: String) = suspendCoroutine<Word?> { continuation ->
        words.document(uuid).get().addOnSuccessListener {
            if (!it.exists()) {
                continuation.resume(null)
                return@addOnSuccessListener
            }

            continuation.resume(it.mapToWord())
        }.addOnFailureListener {
            it.printStackTrace()
            continuation.resume(null)
        }
    }

    private suspend fun findWordProgress(uuid: String) =
        suspendCoroutine<Int> { continuation ->
            learningWords.document(uuid).get().addOnSuccessListener {
                if (!it.exists()) {
                    continuation.resume(WordProgress.NONE)
                    return@addOnSuccessListener
                }

                continuation.resume(it.wordProgress)
            }.addOnFailureListener {
                it.printStackTrace()
                continuation.resume(WordProgress.NONE)
            }
        }

    private fun <T> Task<T>.addCompleteListener(onCompleted: (data: T) -> Unit) {
        if (!this.isSuccessful) {
            exception?.printStackTrace()
        }

        onCompleted(this.result)
    }

    private companion object {
        private const val USERS_COLLECTION = "Users"
        private const val WORDS_COLLECTION = "Dictionary"
        private const val WORDS_KITS_COLLECTION = "Predefined Kits"
        private const val LEARNING_WORDS_COLLECTION = "Learning Words Progress"
        private const val LEARNING_KITS_COLLECTION = "Learning Kits"
    }
}