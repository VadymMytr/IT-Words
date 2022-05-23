package ua.vadymmy.it.words.domain.user

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.user.User

@Reusable
class GetCurrentUserDetailsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<Unit, User>() {

    override suspend fun execute(request: Unit): User {
        return localRepository.getCurrentUserDetails()
    }
}