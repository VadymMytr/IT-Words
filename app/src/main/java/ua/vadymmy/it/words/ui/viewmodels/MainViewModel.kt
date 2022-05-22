package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.AuthHelper
import ua.vadymmy.it.words.utils.emit

class MainViewModel @Inject constructor(
    private val authHelper: AuthHelper
) : BaseViewModel() {

    val navigateAuthLiveData = MutableLiveData(false)

    override fun onResume() {
        if (!authHelper.isUserSignedAndValid) {
            navigateAuthLiveData.emit()
        }
    }
}