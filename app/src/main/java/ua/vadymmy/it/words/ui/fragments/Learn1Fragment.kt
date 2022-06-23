package ua.vadymmy.it.words.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import ua.vadymmy.it.words.databinding.FragmentLearningTestType1Binding
import ua.vadymmy.it.words.domain.models.word.common.Word

class Learn1Fragment : BaseLearnFragment() {

    private lateinit var binding: FragmentLearningTestType1Binding

    override fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLearningTestType1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun bindWord(word: Word) {
        with(binding) {
            learnTest1CardCl.setBackgroundColor(word.image.color)
            learningTest1Rb1.text = answers[0].translate
            learningTest1Rb2.text = answers[1].translate
            learningTest1Rb3.text = answers[2].translate

            onSpeakClick()

            learningTest1WordSpeakButton.setOnClickListener {
                onSpeakClick()
            }

            learningTest1RadioGroup.setOnCheckedChangeListener { _, id ->
                onAnsweredTranslate((requireView().findViewById(id) as RadioButton).text.toString())
            }
        }
    }
}