package ua.vadymmy.it.words.utils

import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton
import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.ui.activities.AuthActivity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthHelper @Inject constructor(
    private val sharedStorageHelper: SharedStorageHelper,
    private val networkHelper: NetworkHelper
) {

    val isUserSignedAndValid
        get() = isUserSigned && currentUserUid.isNotEmpty()

    val currentUserUid: String
        get() {
            return if (isNetworkActive) currentUser?.uid ?: NOT_AUTHORIZED_USER_UID
            else sharedStorageHelper.currentUserUid
        }

    private val auth get() = Firebase.auth
    private val currentUser get() = auth.currentUser
    private val isNetworkActive get() = networkHelper.isConnectionAvailable
    private val isCurrentUserSaved get() = sharedStorageHelper.currentUserUid.isNotEmpty()
    private val isUserSigned: Boolean get() = if (isNetworkActive) auth.currentUser != null else isCurrentUserSaved

    private val configuredUser: User?
        get() = currentUser?.let {
            User(
                uid = it.uid,
                email = it.email ?: NO_EMAIL,
                name = it.displayName ?: NO_NAME,
                photoUrl = it.photoUrl ?: NO_PHOTO.toUri()
            )
        }

    fun onSignInClick(onRequestSignIn: () -> Unit) {
        if (isUserSigned) return
        onRequestSignIn()
    }

    fun onSignOutClick(onLaunchAuthScreen: () -> Unit) {
        if (!isUserSigned) return
        auth.signOut()
        sharedStorageHelper.currentUserUid = NOT_AUTHORIZED_USER_UID
    }

    suspend fun getTokenFromRequestResult(data: Intent?): String =
        suspendCoroutine { continuation ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign In was successful, authenticate with Firebase
                with(task.getResult(ApiException::class.java)!!) {
                    val token = idToken!!
                    continuation.resume(token)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                e.printStackTrace()
                Log.e("TAG", e.message.toString())
                continuation.resume(NOT_AUTHORIZED_USER_UID)
            }
        }

    suspend fun firebaseAuthWithGoogle(token: String, activity: AuthActivity): User? =
        suspendCoroutine { continuation ->
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
                continuation.resume(if (task.isSuccessful) configuredUser else null)
            }
        }

    companion object {
        private const val NOT_AUTHORIZED_USER_UID = ""
        private const val NO_EMAIL = ""
        private const val NO_NAME = ""
        private const val NO_PHOTO = ""
    }
}