package ua.vadymmy.it.words.domain.syncing

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase

@Reusable
class SyncUserDataUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<Unit, Unit>() {

    override suspend fun execute(request: Unit) {
        serverRepository.updateUser(localRepository.getCurrentUserDetails())
    }
}