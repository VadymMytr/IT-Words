package ua.vadymmy.it.words.domain.user

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.user.User

@Reusable
class AuthUserUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<User, Unit>() {

    override suspend fun execute(request: User) {
        serverRepository.loginUser(request)
        localRepository.loginUser(request)
    }
}