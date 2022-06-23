package ua.vadymmy.it.words.ui.common

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.utils.emit

open class BaseViewModel : ViewModel() {
    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailureLiveData = MutableLiveData<Boolean>()
    val showLoaderLiveData = MutableLiveData(false)
    val hideLoaderLiveData = MutableLiveData(false)
    val showMessageLiveData = MutableLiveData(DEFAULT_MESSAGE)

    @CallSuper
    open fun onResume() {
    }

    @CallSuper
    open fun parseIntent(intent: Intent) {
    }

    protected fun onSuccess() {
        onSuccessLiveData.emit()
    }

    protected fun onFailure() {
        onFailureLiveData.emit()
    }

    protected fun loadData(block: suspend () -> Unit) {
        viewModelScope.launch {
            onLoadingStart()
            block()
            onLoadingEnd()
        }
    }

    protected fun showMessage(@StringRes titleRes: Int) {
        showMessageLiveData.postValue(titleRes)
    }

    protected fun onLoadingStart() = showLoader(isLoading = true)

    protected fun onLoadingEnd() = showLoader(isLoading = false)

    private fun showLoader(isLoading: Boolean) {
        showLoaderLiveData.value = isLoading
        hideLoaderLiveData.value = !isLoading
    }

    companion object {
        const val DEFAULT_MESSAGE = -1
    }
}