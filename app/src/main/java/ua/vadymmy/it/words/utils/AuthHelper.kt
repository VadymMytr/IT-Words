package ua.vadymmy.it.words.utils

import androidx.core.net.toUri
import javax.inject.Inject
import javax.inject.Singleton
import ua.vadymmy.it.words.domain.entities.user.User

@Singleton
class AuthHelper @Inject constructor() {

    init {

    }

    val currentUserUid: String get() = User("1", "", "".toUri(), learnProgress = 5).uid
}