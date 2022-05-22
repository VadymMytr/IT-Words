package ua.vadymmy.it.words.utils

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedStorageHelper @Inject constructor(applicationContext: Context) {

    var currentUserUid: String
        get() {
            return sharedPrefs.getString(
                KEY_CURRENT_USER_UID,
                NOT_AUTHORIZED_USER_UID
            ) ?: NOT_AUTHORIZED_USER_UID
        }
        set(value) {
            sharedPrefs.edit().putString(KEY_CURRENT_USER_UID, value).apply()
        }

    private val sharedPrefs = applicationContext.getSharedPreferences(
        SHARED_PREFS,
        Context.MODE_PRIVATE
    )

    private companion object {
        private const val SHARED_PREFS = "shared_prefs"
        private const val KEY_CURRENT_USER_UID = "key_current_user"
        private const val NOT_AUTHORIZED_USER_UID = ""
    }
}