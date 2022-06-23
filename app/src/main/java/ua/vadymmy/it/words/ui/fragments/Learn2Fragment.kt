package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import ua.vadymmy.it.words.databinding.FragmentLearningTestType2Binding
import ua.vadymmy.it.words.domain.models.word.common.Word

class Learn2Fragment : BaseLearnFragment() {

    private lateinit var binding: FragmentLearningTestType2Binding

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLearningTestType2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun bindWord(word: Word) {
        with(binding) {
            learnTest2CardCl.setBackgroundColor(word.image.color)
            learningWordTranslateText.text = word.translate
            learningTest2Rb1.text = answers[0].original
            learningTest2Rb2.text = answers[1].original
            learningTest2Rb3.text = answers[2].original

            learningTest2RadioGroup.setOnCheckedChangeListener { _, id ->
                onAnsweredOrig((requireView().findViewById(id) as RadioButton).text.toString())
            }
        }
    }
}