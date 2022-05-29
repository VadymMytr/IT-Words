package ua.vadymmy.it.words.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordKit
import ua.vadymmy.it.words.domain.words.local.GetAllLearningKitsUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.call
import ua.vadymmy.it.words.utils.emit

class LearningKitsViewModel @Inject constructor(
    private val getAllLearningKitsUseCase: GetAllLearningKitsUseCase,
    private val deleteLearningWordKit: DeleteLearningWordKit
) : BaseViewModel() {

    val learningKitsLiveData = MutableLiveData<List<LearningWordKit>>()
    val openKitDetailsLiveData = MutableLiveData<LearningWordKit?>(null)
    val showNewKitDialogLiveData = MutableLiveData(false)
    val removeKitAtLiveData = MutableLiveData(REMOVE_AT_DEFAULT)

    override fun onResume() {
        super.onResume()
        loadData {
            learningKitsLiveData.value = getAllLearningKitsUseCase(Unit)
        }
    }

    fun onLearningKitClick(learningWordKit: LearningWordKit) {
        openKitDetailsLiveData.call(learningWordKit)
    }

    fun onRemoveLearningKitClick(learningWordKit: LearningWordKit, adapterPosition: Int) {
        loadData {
            deleteLearningWordKit(learningWordKit)
            removeKitAtLiveData.call(adapterPosition)
        }
    }

    fun onCreateKitClick() = showNewKitDialogLiveData.emit()

    companion object {
        const val REMOVE_AT_DEFAULT = -1
    }
}