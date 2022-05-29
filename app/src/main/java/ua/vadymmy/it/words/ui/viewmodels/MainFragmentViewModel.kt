package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.models.user.User
import ua.vadymmy.it.words.domain.user.GetCurrentUserDetailsUseCase
import ua.vadymmy.it.words.domain.user.LogoutUserUseCase
import ua.vadymmy.it.words.domain.words.local.GetLearningWordsAmountUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.emit

class MainFragmentViewModel @Inject constructor(
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase,
    private val getLearningWordsAmountUseCase: GetLearningWordsAmountUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : BaseViewModel() {

    val currentUserLiveData = MutableLiveData<User>()
    val learningWordsAmountLiveData = MutableLiveData<Int>()
    val navigateAuthLiveData = MutableLiveData(false)
    val navigateUserLevelsLiveData = MutableLiveData(false)

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            currentUserLiveData.value = getCurrentUserDetailsUseCase(Unit)
            learningWordsAmountLiveData.value = getLearningWordsAmountUseCase(Unit)
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            logoutUserUseCase {
                navigateAuthLiveData.emit()
            }
        }
    }

    fun onUserLevelsDetailsClick() {
        navigateUserLevelsLiveData.emit()
    }
}