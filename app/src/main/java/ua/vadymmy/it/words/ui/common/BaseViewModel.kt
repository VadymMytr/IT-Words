package ua.vadymmy.it.words.ui.common

import androidx.annotation.CallSuper
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

    @CallSuper
    open fun onResume() {
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

    protected fun onLoadingStart() = showLoader(isLoading = true)

    protected fun onLoadingEnd() = showLoader(isLoading = false)

    private fun showLoader(isLoading: Boolean) {
        showLoaderLiveData.value = isLoading
        hideLoaderLiveData.value = !isLoading
    }
}