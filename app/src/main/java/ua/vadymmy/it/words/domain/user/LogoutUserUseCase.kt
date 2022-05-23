package ua.vadymmy.it.words.domain.user

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.utils.AuthHelper

@Reusable
class LogoutUserUseCase @Inject constructor(
    private val authHelper: AuthHelper
) : BaseUseCase<() -> Unit, Unit>() {

    override suspend fun execute(request: () -> Unit) {
        authHelper.onSignOutClick(request)
    }
}