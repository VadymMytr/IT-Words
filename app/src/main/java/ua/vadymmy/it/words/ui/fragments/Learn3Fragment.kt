package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.FragmentLearningTestType3Binding
import ua.vadymmy.it.words.domain.models.word.common.Word


class Learn3Fragment : BaseLearnFragment() {
    private lateinit var binding: FragmentLearningTestType3Binding

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLearningTestType3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun bindWord(word: Word) {
        with(binding) {
            learnTest3CardCl.setBackgroundColor(word.image.color)
            learningWordTranslateText.text = word.translate
            learningTest3InputText.hint = getString(
                R.string.learning_enter_answer_hint_placeholder,
                word.answerRequiredLength
            )

            learningTest3InputText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    onAnsweredOrig(learningTest3InputText.text.toString())
                }
            }
        }
    }
}