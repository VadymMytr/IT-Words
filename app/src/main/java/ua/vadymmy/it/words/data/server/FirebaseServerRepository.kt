package ua.vadymmy.it.words.data.server

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.data.server.mappers.KEY_ORIGINAL
import ua.vadymmy.it.words.data.server.mappers.KEY_TRANSLATE
import ua.vadymmy.it.words.data.server.mappers.complaintsHashMap
import ua.vadymmy.it.words.data.server.mappers.learningHashMap
import ua.vadymmy.it.words.data.server.mappers.mapToHashMap
import ua.vadymmy.it.words.data.server.mappers.mapToWord
import ua.vadymmy.it.words.data.server.mappers.mapToWordKit
import ua.vadymmy.it.words.data.server.mappers.mapToWordKitInfo
import ua.vadymmy.it.words.data.server.mappers.wordProgress
import ua.vadymmy.it.words.data.server.mappers.wordsUUIDs
import ua.vadymmy.it.words.data.server.mappers.wordsUUIDsHashMap
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordParameters
import ua.vadymmy.it.words.domain.entities.word.common.WordProgress
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitInfo
import ua.vadymmy.it.words.domain.entities.word.search.SearchLocale.EN
import ua.vadymmy.it.words.domain.entities.word.search.SearchLocale.UK
import ua.vadymmy.it.words.domain.entities.word.search.SearchParameters
import ua.vadymmy.it.words.utils.AuthHelper
import ua.vadymmy.it.words.utils.resume
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
            learningKits.document(wordKit.info.uuid)
                .set(wordKit.mapToHashMap())
                .addCompleteListener { continuation.resume() }
        }

    override suspend fun getLearningWordKits(): List<WordKit> {
        val kits = CompletableDeferred<List<WordKit>>()

        learningKits.get().addOnSuccessListener { query ->
            CoroutineScope(Dispatchers.IO).launch {
                if (query.documents.isEmpty()) {
                    kits.complete(emptyList())
                    return@launch
                }

                query.documents.map { doc ->
                    doc.mapToWordKit(getWords(doc.wordsUUIDs))
                }.let {
                    kits.complete(it)
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
            kits.complete(emptyList())
        }

        return kits.await()
    }

    override suspend fun getPredefinedKitsInfo(): List<WordKitInfo> {
        val kitInfoList = CompletableDeferred<List<WordKitInfo>>()

        predefinedKits.get().addOnSuccessListener { query ->
            CoroutineScope(Dispatchers.IO).launch {
                if (query.documents.isEmpty()) {
                    kitInfoList.complete(emptyList())
                    return@launch
                }

                query.documents.filterNot {
                    isPredefinedKitLearning(it.id)
                }.map { doc ->
                    doc.mapToWordKitInfo()
                }.let {
                    kitInfoList.complete(it)
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
            kitInfoList.complete(listOf())
        }

        return kitInfoList.await()
    }

    override suspend fun getPredefinedKitDetails(uuid: String): WordKit {
        val kit = CompletableDeferred<WordKit>()

        predefinedKits.document(uuid).get().addCompleteListener { doc ->
            CoroutineScope(Dispatchers.IO).launch {
                val predefinedKit = doc.mapToWordKit(getWords(doc.wordsUUIDs))
                kit.complete(predefinedKit)
            }
        }

        return kit.await()
    }

    override suspend fun removeLearningWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            learningKits.document(wordKit.info.uuid).delete().addCompleteListener {
                continuation.resume()
            }
        }

    override suspend fun updateLearningWordKit(wordKit: WordKit) =
        suspendCoroutine<Unit> { continuation ->
            learningKits.document(wordKit.info.uuid).set(wordKit.wordsUUIDsHashMap)
                .addCompleteListener {
                    continuation.resume()
                }
        }

    override suspend fun isWordExists(wordParameters: WordParameters) =
        suspendCoroutine<Boolean> { continuation ->
            words.whereEqualTo(KEY_ORIGINAL, wordParameters.original)
                .whereEqualTo(KEY_TRANSLATE, wordParameters.translate)
                .get()
                .addCompleteListener {
                    continuation.resume(it.documents.isNotEmpty())
                }
        }

    override suspend fun searchWords(searchParameters: SearchParameters): List<Word> =
        suspendCoroutine { continuation ->
            val field = when (searchParameters.locale) {
                EN -> KEY_ORIGINAL
                UK -> KEY_TRANSLATE
            }

            words.orderBy(field)
                .startAfter(searchParameters.startAt)
                .endBefore(searchParameters.endBefore)
                .limit(SEARCH_LIMIT)
                .get()
                .addOnSuccessListener {
                    val words = it.documents.map { doc -> doc.mapToWord() }.filter { word ->
                        val text = if (field == KEY_ORIGINAL) word.original else word.translate
                        return@filter text.startsWith(prefix = searchParameters.query)
                    }

                    continuation.resume(words)
                }.addOnFailureListener {
                    it.printStackTrace()
                    continuation.resume(emptyList())
                }
        }

    private suspend fun isPredefinedKitLearning(uuid: String) =
        suspendCoroutine<Boolean> { continuation ->
            learningKits.document(uuid).get().addCompleteListener {
                continuation.resume(it.exists())
            }
        }

    private suspend fun getWords(uuidList: List<String>): List<Word> = uuidList.mapNotNull { uuid ->
        getWord(uuid)
    }

    private suspend fun getWord(uuid: String): Word? {
        return findWord(uuid)?.apply {
            progress = findWordProgress(uuid)
        }
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
        addOnCompleteListener {
            if (!it.isSuccessful) {
                exception?.printStackTrace()
            }

            onCompleted(it.result)
        }
    }

    private companion object {
        private const val USERS_COLLECTION = "Users"
        private const val WORDS_COLLECTION = "Dictionary"
        private const val WORDS_KITS_COLLECTION = "Predefined Kits"
        private const val LEARNING_WORDS_COLLECTION = "Learning Words Progress"
        private const val LEARNING_KITS_COLLECTION = "Learning Kits"
        private const val SEARCH_LIMIT = 10L
    }
}