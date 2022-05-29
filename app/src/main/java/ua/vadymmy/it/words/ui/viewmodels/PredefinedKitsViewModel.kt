package ua.vadymmy.it.words.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.domain.words.local.GetPredefinedKitsPreviewsUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.call

class PredefinedKitsViewModel @Inject constructor(
    val getPredefinedKitsPreviewsUseCase: GetPredefinedKitsPreviewsUseCase
) : BaseViewModel() {

    val predefinedKitsLiveData = MutableLiveData<List<WordKit>>()
    val navigatePredefinedKitDetailsLiveData = MutableLiveData<WordKit?>(null)

    override fun onResume() {
        super.onResume()
        loadData { predefinedKitsLiveData.value = getPredefinedKitsPreviewsUseCase(Unit) }
    }

    fun onPredefinedKitClick(kit: WordKit) {
        Log.i("TAG", "onPredefinedKitClick: $kit")
        navigatePredefinedKitDetailsLiveData.call(kit)
    }
}