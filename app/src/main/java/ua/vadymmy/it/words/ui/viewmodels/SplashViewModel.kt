package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.AuthHelper
import ua.vadymmy.it.words.utils.emit

class SplashViewModel @Inject constructor(
    private val authHelper: AuthHelper
) : BaseViewModel() {

    val navigateMainLiveData = MutableLiveData(false)
    val navigateAuthLiveData = MutableLiveData(false)

    override fun onResume() {
        super.onResume()
        when (authHelper.isUserSignedAndValid) {
            true -> navigateMainLiveData
            false -> navigateAuthLiveData
        }.emit()
    }
}