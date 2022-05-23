package ua.vadymmy.it.words.ui.viewmodels.sync

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.syncing.SyncLearningWordKitsUseCase
import ua.vadymmy.it.words.domain.syncing.SyncPredefinedKitsUseCase
import ua.vadymmy.it.words.domain.syncing.SyncUserDataUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.ui.viewmodels.sync.SyncStatus.Synced
import ua.vadymmy.it.words.ui.viewmodels.sync.SyncStatus.SyncingKits
import ua.vadymmy.it.words.ui.viewmodels.sync.SyncStatus.SyncingProgress
import ua.vadymmy.it.words.utils.emit

class SyncViewModel @Inject constructor(
    private val syncPredefinedKitsUseCase: SyncPredefinedKitsUseCase,
    private val syncLearningWordKitsUseCase: SyncLearningWordKitsUseCase,
    private val syncUserDataUseCase: SyncUserDataUseCase
) : BaseViewModel() {

    val syncStatusLiveData = MutableLiveData<SyncStatus>(SyncingKits)
    val navigateMainLiveData = MutableLiveData(false)

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            syncPredefinedKitsUseCase(Unit)
            syncStatusLiveData.value = SyncingProgress

            syncLearningWordKitsUseCase(Unit)
            syncUserDataUseCase(Unit)
            syncStatusLiveData.value = Synced
        }
    }

    fun onNextClick() {
        navigateMainLiveData.emit()
    }
}