package ua.vadymmy.it.words.ui.viewmodels

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.search.SearchParameters
import ua.vadymmy.it.words.domain.words.both.AddLearningWordToKitUseCase
import ua.vadymmy.it.words.domain.words.both.AddLearningWordToKitUseCase.AddWordQuery
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase.DeleteWordQuery
import ua.vadymmy.it.words.domain.words.server.SearchWordsUseCase
import ua.vadymmy.it.words.domain.words.utils.PronounceWordUseCase
import ua.vadymmy.it.words.ui.activities.SearchActivity.Companion.KEY_SEARCH_BUNDLE_KIT
import ua.vadymmy.it.words.ui.common.BaseViewModel

class SearchViewModel @Inject constructor(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val pronounceWordUseCase: PronounceWordUseCase,
    private val addLearningWordToKitUseCase: AddLearningWordToKitUseCase,
    private val deleteLearningWordFromKitUseCase: DeleteLearningWordFromKitUseCase
) : BaseViewModel() {

    val searchResultLiveData = MutableLiveData<List<Word>>()
    val searchNotFoundLiveData = MutableLiveData(false)
    val searchShowDefaultLiveData = MutableLiveData(true)

    private var kit: LearningWordKit? = null

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        kit = intent.getSerializableExtra(KEY_SEARCH_BUNDLE_KIT) as LearningWordKit
    }

    fun search(query: String) {
        if (query.isEmpty()) return

        loadData {
            val words = searchWordsUseCase(SearchParameters(query = query))
            searchShowDefaultLiveData.value = false
            searchNotFoundLiveData.value = words.isEmpty()
            searchResultLiveData.value = words
        }
    }

    fun onSpeakWordClick(word: Word) {
        viewModelScope.launch {
            pronounceWordUseCase(word)
        }
    }

    fun onAddWordClick(word: Word) {
        kit.inBackground {
            addLearningWordToKitUseCase(AddWordQuery(it, word))
            showMessage(R.string.search_added_to_kit)
        }
    }

    fun onRemoveWordClick(word: Word) {
        kit.inBackground {
            val index = kit.indexOf(word).takeIf { index ->
                index != DEFAULT_INDEX
            } ?: return@inBackground

            deleteLearningWordFromKitUseCase(DeleteWordQuery(it, index))
            showMessage(R.string.search_removed_from_kit)
        }
    }

    fun isWordInKit(word: Word): Boolean = kit?.words?.any { it.uuid == word.uuid } ?: false

    private fun LearningWordKit?.inBackground(block: suspend (LearningWordKit) -> Unit) {
        this ?: return
        viewModelScope.launch {
            block(this@inBackground)
        }
    }

    private fun LearningWordKit?.indexOf(word: Word) = this?.words?.indexOfFirst {
        it.uuid == word.uuid
    } ?: DEFAULT_INDEX

    private companion object {
        private const val DEFAULT_INDEX = -1
    }
}