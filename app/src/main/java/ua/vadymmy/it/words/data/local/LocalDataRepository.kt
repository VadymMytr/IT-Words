package ua.vadymmy.it.words.data.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ClientRepository
import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.utils.AuthHelper

@Reusable
class LocalDataRepository @Inject constructor(
    private val authHelper: AuthHelper
) : ClientRepository {

    override suspend fun loginUser(user: User) {
    }

    override suspend fun updateUser(user: User) {
    }

    override suspend fun updateWordProgress(word: Word) {
    }

    override suspend fun addLearningWordKit(wordKit: WordKit) {
    }

    override suspend fun updateLearningWordKit(wordKit: WordKit) {
    }

    override suspend fun removeLearningWordKit(wordKit: WordKit) {
    }
}