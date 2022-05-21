package ua.vadymmy.it.words.utils

import javax.inject.Inject
import javax.inject.Singleton
import ua.vadymmy.it.words.domain.entities.user.User

@Singleton
class AuthHelper @Inject constructor() {

    val currentUser: User get() = User("1", "")
}