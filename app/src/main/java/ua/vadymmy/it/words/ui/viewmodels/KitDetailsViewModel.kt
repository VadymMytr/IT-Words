package ua.vadymmy.it.words.ui.viewmodels

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.domain.words.both.AddLearningWordKitUseCase
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase.DeleteWordQuery
import ua.vadymmy.it.words.domain.words.local.GetPredefinedKitWordsUseCase
import ua.vadymmy.it.words.domain.words.utils.PronounceWordUseCase
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity.Companion.KEY_BUNDLE_WORD_KIT
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.REMOVE_AT_DEFAULT
import ua.vadymmy.it.words.utils.call

class KitDetailsViewModel @Inject constructor(
    private val getPredefinedKitWordsUseCase: GetPredefinedKitWordsUseCase,
    private val deleteLearningWordFromKitUseCase: DeleteLearningWordFromKitUseCase,
    private val addLearningWordKitUseCase: AddLearningWordKitUseCase,
    private val pronounceWordUseCase: PronounceWordUseCase
) : BaseViewModel() {

    val predefinedWordKitLiveData = MutableLiveData<WordKit?>(null)
    val learningWordKitLiveData = MutableLiveData<LearningWordKit?>(null)
    val removeWordAtLiveData = MutableLiveData(REMOVE_AT_DEFAULT)
    val navigateToSearchLiveData = MutableLiveData<LearningWordKit?>(null)
    val navigateToTestLiveData = MutableLiveData<LearningWordKit?>(null)
    val navigateToCardsLiveData = MutableLiveData<WordKit?>(null)

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        loadData {
            val wordKit = intent.getSerializableExtra(KEY_BUNDLE_WORD_KIT) as WordKit
            if (wordKit is LearningWordKit) {
                learningWordKitLiveData.value = wordKit
            } else {
                with(wordKit.words) {
                    clear()
                    addAll(getPredefinedKitWordsUseCase(wordKit))
                }

                predefinedWordKitLiveData.value = wordKit
            }
        }
    }

    fun onCardsClick() {
        val wordKit = predefinedWordKitLiveData.value
        val learningWordKit = learningWordKitLiveData.value

        fun navigateCards(wordKit: WordKit) {
            navigateToCardsLiveData.call(wordKit)
        }

        wordKit?.let { navigateCards(it) }
        learningWordKit?.let { navigateCards(it) }
    }

    fun onAddWordsClick() {
        learningWordKitLiveData.value?.let {
            navigateToSearchLiveData.call(it)
        }
    }

    fun onSpeakWordClick(word: Word) = viewModelScope.launch { pronounceWordUseCase(word) }

    fun onRemoveWordClick(word: Word, adapterPosition: Int) = loadData {
        val kit = learningWordKitLiveData.value ?: return@loadData
        val indexOf = kit.words.indexOf(word).takeIf {
            it != REMOVE_AT_DEFAULT
        } ?: return@loadData

        deleteLearningWordFromKitUseCase(DeleteWordQuery(kit, indexOf))
        removeWordAtLiveData.call(adapterPosition)
        learningWordKitLiveData.value = kit
    }

    fun onLearnWordsClick() = loadData {
        predefinedWordKitLiveData.value?.let { predefinedKit ->
            val learningKit = LearningWordKit(predefinedKit, predefinedKit.uuid)
            addLearningWordKitUseCase(learningKit)
            navigateToTestLiveData.call(learningKit)
        }

        learningWordKitLiveData.value?.let { learningWordKit ->
            navigateToTestLiveData.call(learningWordKit)
        }
    }
}