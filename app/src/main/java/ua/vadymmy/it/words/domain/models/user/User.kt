package ua.vadymmy.it.words.domain.models.user

import android.net.Uri
import androidx.core.net.toUri
import ua.vadymmy.it.words.data.local.entities.user.UserEntity

data class User(
    val uid: String,
    val email: String,
    val photoUrl: Uri,
    val name: String = EMPTY_NAME,
    val learnProgress: Int = NO_PROGRESS
) {

    val level: UserLevel
        get() = UserLevel.fromProgress(learnProgress)

    constructor(userEntity: UserEntity) : this(
        userEntity.user_id,
        userEntity.user_email,
        userEntity.user_photo_url.toUri(),
        userEntity.user_name,
        userEntity.user_learn_progress
    )

    private companion object {
        private const val EMPTY_NAME = ""
        private const val NO_PROGRESS = 0
    }
}