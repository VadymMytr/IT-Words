package ua.vadymmy.it.words.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.vadymmy.it.words.utils.emit

open class BaseViewModel : ViewModel() {
    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailureLiveData = MutableLiveData<Boolean>()

    open fun onResume() {}

    protected fun onSuccess() {
        onSuccessLiveData.emit()
    }

    protected fun onFailure() {
        onFailureLiveData.emit()
    }
}