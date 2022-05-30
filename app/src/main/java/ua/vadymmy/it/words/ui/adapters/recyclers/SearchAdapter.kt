package ua.vadymmy.it.words.ui.adapters.recyclers

import androidx.core.view.isVisible
import ua.vadymmy.it.words.databinding.ItemWordBinding
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

class SearchAdapter(
    onSpeakWordClick: (Word) -> Unit,
    private val onWordAddClick: (Word) -> Unit,
    private val onWordRemoveClick: (Word) -> Unit,
    private val isWordInLearningKit: (Word) -> Boolean
) : WordsAdapter(onSpeakWordClick) {

    override fun removeAt(position: Int) {
        //noop
    }

    override fun setWords(wordKit: WordKit) {
        //noop
    }

    override fun setWords(wordKit: LearningWordKit) {
        //noop
    }

    fun setWords(words: List<Word>) {
        elements = words.toMutableList()
    }

    override fun updateWordButtons(binding: ItemWordBinding, element: Word, adapterPosition: Int) {
        super.updateWordButtons(binding, element, adapterPosition)
        with(binding) {
            fun setButtonsVisibility(isWordInKit: Boolean) {
                wordItemDeleteButton.isVisible = isWordInKit
                wordItemAddButton.isVisible = !isWordInKit
            }

            wordItemDeleteButton.setOnClickListener {
                onWordRemoveClick(element)
                setButtonsVisibility(isWordInKit = false)
            }

            wordItemAddButton.setOnClickListener {
                onWordAddClick(element)
                setButtonsVisibility(isWordInKit = true)
            }

            setButtonsVisibility(isWordInLearningKit(element))
        }
    }
}