package ua.vadymmy.it.words.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.words.inserts.AddWordToDictionaryUseCase
import ua.vadymmy.it.words.domain.words.selections.GetLearningWordKitsUseCase
import ua.vadymmy.it.words.domain.words.selections.GetPredefinedKitDetailsUseCase
import ua.vadymmy.it.words.domain.words.selections.GetPredefinedKitsInfoListUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel

class MainViewModel @Inject constructor(
    private val getPredefinedKitsInfoListUseCase: GetPredefinedKitsInfoListUseCase,
    private val getLearningWordKitsUseCase: GetLearningWordKitsUseCase,
    private val addWordToDictionaryUseCase: AddWordToDictionaryUseCase,
    private val getPredefinedKitDetailsUseCase: GetPredefinedKitDetailsUseCase
) : BaseViewModel() {

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            Log.i("TAG_", "get predefined kits")
            val kits = addWordToDictionaryUseCase(Word("", "", "", WordImage.empty))
            Log.i("TAG_", "get predefined kits RESULT $kits")

//            Log.i("TAG_", "get predefined kit details")
//            val firstKitDetails = kits
//                .findLast { it.category == PROGRAMMING }
//                ?.let { getPredefinedKitDetailsUseCase(it) }
//            Log.i("TAG_", "get predefined kit details RESULT ${firstKitDetails?.size}")
        }
    }

}