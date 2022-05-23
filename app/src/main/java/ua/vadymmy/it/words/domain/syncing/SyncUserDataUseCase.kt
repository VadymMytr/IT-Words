package ua.vadymmy.it.words.domain.syncing

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.utils.AuthHelper

@Reusable
class SyncUserDataUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository,
    private val authHelper: AuthHelper
) : BackgroundUseCase<Unit, Unit>() {

    override suspend fun execute(request: Unit) {
        val authUid = authHelper.currentUserUid
        val serverUserData = serverRepository.getUser(authUid)
        val localUserData = localRepository.getCurrentUserDetails()

        val serverProgress = serverUserData?.learnProgress ?: NO_PROGRESS
        if (serverProgress > localUserData.learnProgress) {
            localRepository.updateUser(localUserData.apply {
                learnProgress = serverProgress
            })
        }

        serverRepository.updateUser(localUserData)
    }

    companion object {
        private const val NO_PROGRESS = 0
    }
}