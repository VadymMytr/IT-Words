package ua.vadymmy.it.words.data.local

import android.util.Log
import javax.inject.Inject
import ua.vadymmy.it.words.data.local.dao.UserDao
import ua.vadymmy.it.words.data.local.dao.WordsDao
import ua.vadymmy.it.words.data.local.entities.binding.LearningKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.PredefinedKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningKitsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningWordsEntity
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.utils.AuthHelper

class RoomDataRepository @Inject constructor(
    private val authHelper: AuthHelper,
    private val userDao: UserDao,
    private val wordsDao: WordsDao
) : LocalRepository {

    private val userId get() = authHelper.currentUserUid

    override suspend fun loginUser(user: User) {
        with(userDao) {
            val userEntity = UserEntity(user)
            if (isUserExists(user.uid)) updateUser(userEntity)
            else insertUser(userEntity)
        }
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(UserEntity(user))
    }

    override suspend fun getCurrentUserDetails(): User = User(userDao.getUserDetails(userId))

    override suspend fun addPredefinedWordKit(wordKit: WordKit) {
        with(wordsDao) {
            if (!isKitExists(wordKit.uuid)) insertPredefinedWordKit(PredefinedWordKitEntity(wordKit))
            Log.i("TAG", "kit with uuid ${wordKit.uuid} inserted")
            wordKit.words.map { WordEntity(it) }.forEach {
                if (!isWordExists(it.word_uuid)) insertWord(it)
                Log.i("TAG", "word with uuid ${it.word_uuid}")
                insertKitWordBinding(PredefinedKitsWordsEntity(wordKit, it))
            }
        }
    }

    override suspend fun addComplaintOn(word: Word) {
        with(wordsDao) {
            word.complaintsAmount++
            updateWordComplaints(word.uuid, word.complaintsAmount)
        }
    }

    override suspend fun addLearningWordKit(learningWordKit: LearningWordKit) {
        with(wordsDao) {
            insertLearningWordKit(LearningWordKitEntity(learningWordKit))
            insertUserKitBinding(
                UsersLearningKitsEntity(
                    userId,
                    learningWordKit
                )
            ) //bind user to learn kit

            learningWordKit.words.forEach {
                addLearningWordToKit(
                    kitUUID = learningWordKit.uuid,
                    word = it
                )
            }
        }
    }

    override suspend fun addLearningWordToKit(kitUUID: String, word: Word) {
        with(wordsDao) {
            if (!isWordExists(word.uuid)) insertWord(WordEntity(word))

            val learningWordBinding = UsersLearningWordsEntity(userId, word) // bind user to word
            if (isWordLearning(word.uuid, userId)) {
                updateLearningWord(word)
            } else {
                insertLearningWordBinding(learningWordBinding)
            }

            insertKitWordBinding(LearningKitsWordsEntity(kitUUID, word))
        }
    }

    override suspend fun deleteLearningWordFromKit(kitUUID: String, word: Word) {
        wordsDao.deleteLearningWordFromKit(kitUUID = kitUUID, wordUUID = word.uuid)
    }

    override suspend fun updateLearningWordKit(learningWordKit: LearningWordKit) {
        with(wordsDao) {
            updateLearningWordKit(LearningWordKitEntity(learningWordKit))
            learningWordKit.words.forEach {
                updateLearningWord(it)
            }
        }
    }

    override suspend fun removeLearningWordKit(learningWordKit: LearningWordKit) {
        wordsDao.deleteLearningWordKit(LearningWordKitEntity(learningWordKit))
    }

    override suspend fun getLearningWordKits(): List<LearningWordKit> {
        val learningKits = mutableListOf<LearningWordKit>()

        wordsDao.getAllLearningPreviews(userId).forEach {
            val words = wordsDao.getLearningKitWords(userId, it.word_kit_uuid).map { kitWithWords ->
                Word(kitWithWords.wordEntity, kitWithWords.progress)
            }

            learningKits.add(LearningWordKit(it, words.toMutableList()))
        }

        return learningKits
    }

    override suspend fun getRandomWords(amount: Int): List<Word> {
        return wordsDao.getRandomWords(amount).map { Word(it) }
    }

    override suspend fun getPredefinedWordKitsPreviews(): List<WordKit> {
        val predefinedKitsPreviews = wordsDao.getAllPredefinedKitsPreviews().filterNot {
            wordsDao.isKitLearning(it.word_kit_uuid)
        }

        return predefinedKitsPreviews.map { WordKit(it) }
    }

    override suspend fun getPredefinedWordKitWords(kitUUID: String): List<Word> {
        val predefinedKitWithWords = wordsDao.getPredefinedKitWords(kitUUID)
        predefinedKitWithWords.firstOrNull()?.wordKit ?: return listOf()

        return predefinedKitWithWords.map { Word(it.word) }.toMutableList()
    }

    override suspend fun getLearningWordsAmount(): Int {
        return getLearningWordKits().sumOf { it.words.size }
    }

    private suspend fun updateLearningWord(word: Word) {
        wordsDao.updateLearningWordBinding(
            wordUUID = word.uuid,
            progress = word.progress,
            userId = userId
        )
    }
}