package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

interface DataRepository {
    suspend fun loginUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun updateWordProgress(word: Word)
    suspend fun getPredefinedWordKits(): List<WordKit>
    suspend fun getLearningWordKits(): List<LearningWordKit>
    suspend fun addLearningWordKit(learningWordKit: LearningWordKit)
    suspend fun updateLearningWordKit(learningWordKit: LearningWordKit)
    suspend fun removeLearningWordKit(learningWordKit: LearningWordKit)
}