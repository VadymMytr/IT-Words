package ua.vadymmy.it.words.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase
import ua.vadymmy.it.words.domain.words.local.GetAllLearningKitsUseCase
import ua.vadymmy.it.words.domain.words.local.GetLearningWordsAmountUseCase
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.gson

class MainViewModel @Inject constructor(
    private val getLearningWordsAmountUseCase: GetLearningWordsAmountUseCase,
    private val getAllLearningKitsUseCase: GetAllLearningKitsUseCase,
    private val deleteLearningWordFromKitUseCase: DeleteLearningWordFromKitUseCase
) : BaseViewModel() {

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
//            loginUserUseCase(User("1", "", "".toUri(), learnProgress = 5))

//            Log.i("TAG_", "download predef kits:")
//            val kitsFromServer = downloadPredefinedWordKitsUseCase(Unit)
//            Log.i("TAG_", "save predef kits:")
//            kitsFromServer.forEach {
//                savePredefinedWordKitUseCase(it)
//            }
//            Log.i("TAG_", "saved")

//            Log.i("TAG_", "get predef kits:")
//            val kits = getPredefinedKitsPreviewsUseCase(Unit)
//            Log.i("TAG_", "${kits.size}")
//
//            Log.i("TAG_", "get predef kit ${kits.first().name} details")
//            val details = getPredefinedKitDetailsUseCase(kits.first().uuid)
//            Log.i("TAG_", "received details, words: ${details?.words?.size}")
//
//            details?.let {
//                Log.i("TAG_", "create learning from predef ${it.name}:")
//                val learning = LearningWordKit(it, it.uuid)
//                Log.i("TAG_", "created, save to db")
//                saveLearningWordKitUseCase(learning)
//            }
//
//            val noWords = LearningWordKit("fask", WordImage.empty, MATH, mutableListOf(), "f07215a3-c8c6-4462-a588-913adb5a1fb3")
//            updateLearningWordKitUseCase(noWords)

            val learnKit = getAllLearningKitsUseCase(Unit).first()
            Log.i("TAG_", "learn amount: ${getLearningWordsAmountUseCase(Unit)}")
            Log.i("TAG_", "kit: ${gson.toJson(learnKit)}")
        }
    }
}