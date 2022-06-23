package ua.vadymmy.it.words.ui.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.words.both.UpdateLearningWordKitUseCase
import ua.vadymmy.it.words.domain.words.local.GetWordTestingAnswersUseCase
import ua.vadymmy.it.words.domain.words.utils.PronounceWordUseCase
import ua.vadymmy.it.words.ui.activities.KitDetailsActivity
import ua.vadymmy.it.words.ui.common.BaseViewModel
import ua.vadymmy.it.words.utils.call

class LearningViewModel @Inject constructor(
    private val getWordTestingAnswersUseCase: GetWordTestingAnswersUseCase,
    private val pronounceWordUseCase: PronounceWordUseCase,
    private val updateLearningWordKitUseCase: UpdateLearningWordKitUseCase
) : BaseViewModel() {

    val showProgressLiveData = MutableLiveData<Pair<Int, Int>>(0 to 0)
    val showCurrentWordLiveData = MutableLiveData<Word>()
    val updateFragmentVisibilityLiveData = MutableLiveData<Boolean>()
    val updateCurrentWordVisibilityLiveData: LiveData<Boolean> =
        Transformations.map(updateFragmentVisibilityLiveData) {
            !it
        }

    val updateIDKButtonText = MutableLiveData<Int>()
    val navigateNextWordLiveData = MutableLiveData<Pair<Word, List<Word>>?>(null)
    val navigateResultLiveData = MutableLiveData<Pair<LearningWordKit, Int>?>(null)

    private var learningKit: LearningWordKit? = null
    private var currentPosition = -1
    private var correctAnswers = 0
    private val isShowingWordDetailsNow get() = updateCurrentWordVisibilityLiveData.value ?: false
    private val pages: Int
        get() = learningKit?.size?.let {
            if (it > MAXIMUM_LEARNED) MAXIMUM_LEARNED
            else it
        } ?: 0

    private val currentWord get() = learningKit?.words?.get(currentPosition)

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        loadData {
            learningKit = (intent.getSerializableExtra(
                KitDetailsActivity.KEY_BUNDLE_WORD_KIT
            ) as LearningWordKit)

            openNextScreen()
        }
    }

    fun onButtonClick() {
        if (isShowingWordDetailsNow) {
            openNextScreen()
        } else {
            onAnswered(false)
        }
    }

    fun onPronounceClick() {
        currentWord?.let {
            viewModelScope.launch {
                pronounceWordUseCase(it)
            }
        }
    }

    fun checkAnswer(answer: String) {
        currentWord?.let {
            onAnswered(it.isCorrectAnswer(answer))
        }
    }

    fun checkTranslate(translate: String) {
        currentWord?.let {
            onAnswered(it.isCorrectTranslate(translate))
        }
    }

    private fun onAnswered(isCorrectAnswer: Boolean) {
        if (isCorrectAnswer) {
            correctAnswers++
            viewModelScope.launch {
                currentWord?.let {
                    it.progress++
                }

                learningKit?.let {
                    Log.i("TAG", "answers correct: $correctAnswers")
                    updateLearningWordKitUseCase(it)
                }
            }
        }

        showMessageLiveData.postValue(if (isCorrectAnswer) R.string.learning_answer_correct else R.string.learning_answer_incorrect)
        if (currentPosition == pages - 1) {
            learningKit?.let {
                navigateResultLiveData.call(it to correctAnswers)
            }
        } else {
            showWordDetails()
        }
    }

    private fun showWordDetails() {
        currentWord?.let {
            showCurrentWordLiveData.call(it)
            updateFragmentVisibilityLiveData.value = false
            updateIDKButtonText.value = R.string.syncing_next
        }
    }

    private fun openNextScreen() {
        currentPosition++
        updateIDKButtonText.value = R.string.learning_i_dont_know
        showProgressLiveData.value = currentPosition.inc() to pages

        val word = learningKit?.words?.get(currentPosition) ?: return
        viewModelScope.launch {
            val answers = getWordTestingAnswersUseCase(word)
            navigateNextWordLiveData.call(word to answers)
            updateFragmentVisibilityLiveData.value = true
        }
    }

    companion object {
        private const val MAXIMUM_LEARNED = 10
    }
}