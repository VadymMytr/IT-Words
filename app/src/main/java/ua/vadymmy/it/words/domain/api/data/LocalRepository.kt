package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

interface LocalRepository : DataRepository {
    suspend fun getCurrentUserDetails(): User
    suspend fun addPredefinedWordKit(wordKit: WordKit)
    suspend fun getPredefinedWordKitsPreviews(): List<WordKit>
    suspend fun getPredefinedWordKitWords(kitUUID: String): List<Word>
    suspend fun addLearningWordToKit(kitUUID: String, word: Word)
    suspend fun deleteLearningWordFromKit(kitUUID: String, word: Word)
    suspend fun getLearningWordsAmount(): Int
    suspend fun getRandomWords(amount: Int): List<Word>
}