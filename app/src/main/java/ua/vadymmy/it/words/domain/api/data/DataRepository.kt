package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit

interface DataRepository {
    suspend fun loginUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun addComplaintOn(word: Word)
    suspend fun getLearningWordKits(): List<LearningWordKit>
    suspend fun addLearningWordKit(learningWordKit: LearningWordKit)
    suspend fun updateLearningWordKit(learningWordKit: LearningWordKit)
    suspend fun removeLearningWordKit(learningWordKit: LearningWordKit)
}