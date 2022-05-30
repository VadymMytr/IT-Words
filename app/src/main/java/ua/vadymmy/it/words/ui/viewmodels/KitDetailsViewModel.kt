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
    val wordKitSizeLiveData = MutableLiveData(DEFAULT_SIZE)
    val wordKitProgressLiveData = MutableLiveData<KitProgress>()
    val removeWordAtLiveData = MutableLiveData(REMOVE_AT_DEFAULT)
    val navigateToSearchLiveData = MutableLiveData<LearningWordKit?>(null)
    val navigateToTestLiveData = MutableLiveData<LearningWordKit?>(null)
    val navigateToLearningKitDetailsLiveData = MutableLiveData<LearningWordKit?>(null)
    val navigateToCardsLiveData = MutableLiveData<WordKit?>(null)

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        loadData {
            val wordKit = intent.getSerializableExtra(KEY_BUNDLE_WORD_KIT) as WordKit

            if (wordKit is LearningWordKit) {
                learningWordKitLiveData.value = wordKit
                wordKitProgressLiveData.value = KitProgress.from(wordKit)
            } else {
                with(wordKit.words) {
                    clear()
                    addAll(getPredefinedKitWordsUseCase(wordKit))
                }

                predefinedWordKitLiveData.value = wordKit
            }

            wordKitSizeLiveData.value = wordKit.size
        }
    }

    override fun onResume() {
        super.onResume()
        predefinedWordKitLiveData.value?.let { predefinedKit ->
            predefinedWordKitLiveData.value = predefinedKit
        }

        learningWordKitLiveData.value?.let { learningWordKit ->
            predefinedWordKitLiveData.value = learningWordKit
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

    fun onRemoveWordClick(word: Word, adapterPosition: Int) = viewModelScope.launch {
        val kit = learningWordKitLiveData.value ?: return@launch
        val indexOf = kit.words.indexOf(word).takeIf {
            it != REMOVE_AT_DEFAULT
        } ?: return@launch

        deleteLearningWordFromKitUseCase(DeleteWordQuery(kit, indexOf))
        removeWordAtLiveData.call(adapterPosition)
        wordKitSizeLiveData.value = kit.size
        wordKitProgressLiveData.value = KitProgress.from(kit)
        if (kit.isEmpty) learningWordKitLiveData.value = kit
    }

    fun onLearnWordsClick() = loadData {
        predefinedWordKitLiveData.value?.let { predefinedKit ->
            val learningKit = LearningWordKit(predefinedKit, predefinedKit.uuid)
            addLearningWordKitUseCase(learningKit)
            navigateToLearningKitDetailsLiveData.call(learningKit)
        }

        learningWordKitLiveData.value?.let { learningWordKit ->
            navigateToTestLiveData.call(learningWordKit)
        }
    }

    data class KitProgress(val progressPercent: Int, val progress: Int, val size: Int) {
        companion object {
            fun from(kit: LearningWordKit) = KitProgress(
                kit.learnProgressPercent.toInt(),
                kit.learnProgress,
                kit.size
            )
        }
    }

    companion object {
        const val DEFAULT_SIZE = 0
    }
}