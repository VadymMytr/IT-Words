package ua.vadymmy.it.words.data.server.mappers

import androidx.core.net.toUri
import com.google.firebase.firestore.DocumentSnapshot
import ua.vadymmy.it.words.domain.entities.user.User
import ua.vadymmy.it.words.utils.findInt
import ua.vadymmy.it.words.utils.findString

private const val KEY_EMAIL = "user_email"
private const val KEY_NAME = "user_name"
private const val KEY_PHOTO_URL = "user_photo_url"
private const val KEY_PROGRESS = "user_progress"

fun User.mapToHashMap(): HashMap<String, Any> = hashMapOf(
    KEY_EMAIL to email,
    KEY_NAME to name,
    KEY_PHOTO_URL to photoUrl.toString(),
    KEY_PROGRESS to learnProgress
)

fun DocumentSnapshot.mapToUser(): User = User(
    uid = id,
    email = findString(KEY_EMAIL),
    name = findString(KEY_NAME),
    photoUrl = findString(KEY_PHOTO_URL).toUri(),
    learnProgress = findInt(KEY_PROGRESS)
)