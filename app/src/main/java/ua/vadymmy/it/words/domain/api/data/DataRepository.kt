package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

interface DataRepository {
    suspend fun loginUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun updateWordProgress(word: Word)
    suspend fun getLearningWordKits(): List<WordKit>
    suspend fun addLearningWordKit(wordKit: WordKit)
    suspend fun updateLearningWordKit(wordKit: WordKit)
    suspend fun removeLearningWordKit(wordKit: WordKit)
}