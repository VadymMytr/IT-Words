package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordKit
import ua.vadymmy.it.words.domain.words.local.GetAllLearningKitsUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.REMOVE_AT_DEFAULT
import ua.vadymmy.it.words.utils.call
import ua.vadymmy.it.words.utils.emit

class LearningKitsViewModel @Inject constructor(
    private val getAllLearningKitsUseCase: GetAllLearningKitsUseCase,
    private val deleteLearningWordKit: DeleteLearningWordKit
) : BaseViewModel() {

    val learningKitsLiveData = MutableLiveData<MutableList<LearningWordKit>>()
    val openKitDetailsLiveData = MutableLiveData<LearningWordKit?>(null)
    val showNewKitDialogLiveData = MutableLiveData(false)
    val removeKitAtLiveData = MutableLiveData(REMOVE_AT_DEFAULT)

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            learningKitsLiveData.value = getAllLearningKitsUseCase(Unit).toMutableList()
        }
    }

    fun onLearningKitClick(learningWordKit: LearningWordKit) {
        openKitDetailsLiveData.call(learningWordKit)
    }

    fun onRemoveLearningKitClick(learningWordKit: LearningWordKit, adapterPosition: Int) {
        viewModelScope.launch {
            deleteLearningWordKit(learningWordKit)
            removeKitAtLiveData.call(adapterPosition)
            learningKitsLiveData.value?.let {
                it.remove(learningWordKit)
                if (it.isEmpty()) learningKitsLiveData.value = it
            }
        }
    }

    fun onCreateKitClick() = showNewKitDialogLiveData.emit()
}