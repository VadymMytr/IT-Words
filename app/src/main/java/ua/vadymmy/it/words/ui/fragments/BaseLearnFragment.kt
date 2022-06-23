package ua.vadymmy.it.words.ui.fragments

import androidx.lifecycle.LifecycleOwner
import ua.vadymmy.it.words.di.AppComponent
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.ui.activities.testing.LearningActivity
import ua.vadymmy.it.words.ui.common.BaseFragment
import ua.vadymmy.it.words.ui.viewmodels.LearningViewModel

abstract class BaseLearnFragment : BaseFragment() {
    var word: Word? = null
    var answers = listOf<Word>()
    private val activityViewModel: LearningViewModel get() = (requireActivity() as LearningActivity).viewModel

    override fun observe(lifecycleOwner: LifecycleOwner) {}
    override fun injectFragment(injector: AppComponent) {}
    override fun configureViews() {
        word?.let {
            bindWord(it)
        }
    }

    protected abstract fun bindWord(word: Word)

    protected fun onAnsweredOrig(answer: String) {
        activityViewModel.checkAnswer(answer)
    }

    protected fun onAnsweredTranslate(translate: String) {
        activityViewModel.checkTranslate(translate)
    }

    protected fun onSpeakClick() {
        activityViewModel.onPronounceClick()
    }
}