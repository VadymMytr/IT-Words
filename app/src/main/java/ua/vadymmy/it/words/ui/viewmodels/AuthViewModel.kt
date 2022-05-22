package ua.vadymmy.it.words.ui.viewmodels

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.user.AuthUserUseCase
import ua.vadymmy.it.words.ui.activities.AuthActivity
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.AuthHelper
import ua.vadymmy.it.words.utils.emit

class AuthViewModel @Inject constructor(
    private val authHelper: AuthHelper,
    private val authUser: AuthUserUseCase
) : BaseViewModel() {

    val requestGoogleSignInLiveData = MutableLiveData(false)
    val requestFirebaseSignInLiveData = MutableLiveData(EMPTY_TOKEN)

    fun onSignInClick() {
        authHelper.onSignInClick {
            requestGoogleSignInLiveData.emit()
        }
    }

    fun onSignInRequestResult(data: Intent?) {
        viewModelScope.launch {
            val token = authHelper.getTokenFromRequestResult(data)
            if (token.isNotEmpty()) {
                requestFirebaseSignInLiveData.value = token
            } else {
                onFailure()
            }
        }
    }

    fun requestFirebaseAuth(token: String, activity: AuthActivity) {
        viewModelScope.launch {
            authHelper.firebaseAuthWithGoogle(token, activity)?.let {
                authUser(it)
                onSuccess()
            } ?: onFailure()
        }
    }


    private companion object {
        private const val EMPTY_TOKEN = ""
    }
}