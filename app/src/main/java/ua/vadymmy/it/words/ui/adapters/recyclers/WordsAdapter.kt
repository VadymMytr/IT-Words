package ua.vadymmy.it.words.ui.adapters.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import ua.vadymmy.it.words.databinding.ItemWordBinding
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.InProgress
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.Learned
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.None
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.ui.common.BaseAdapter

class WordsAdapter(
    private val onSpeakWordClick: (Word) -> Unit,
    private val onWordRemoveClick: (Word, adapterPosition: Int) -> Unit = { _, _ -> }
) : BaseAdapter<ItemWordBinding, Word>() {

    private var isLearningKit: Boolean = false

    fun setWords(wordKit: WordKit) {
        elements = wordKit.words.toMutableList()
        isLearningKit = false
    }

    fun setWords(wordKit: LearningWordKit) {
        elements = wordKit.words.toMutableList()
        isLearningKit = true
    }

    override fun inflateBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemWordBinding.inflate(layoutInflater, parent, false)

    override fun ItemWordBinding.onBindElement(
        element: Word,
        context: Context,
        adapterPosition: Int
    ) {
        wordItemOriginal.text = element.original
        wordItemTranslate.text = element.translate
        wordItemDeleteButton.isVisible = isLearningKit

        when (val wordProgress = element.wordProgress) {
            is Learned -> {
                wordItemProgressLayout.isVisible = false
                wordItemDoneImage.isVisible = true
            }

            is InProgress -> {
                wordItemProgressLayout.isVisible = true
                wordItemDoneImage.isVisible = false
                wordItemProgressBar.progress = wordProgress.progress.toFloat()
            }

            is None -> {
                //noop
            }
        }

        wordItemSpeakButton.setOnClickListener {
            onSpeakWordClick(element)
            wordItemSpeakButton.playAnimation()
        }

        wordItemDeleteButton.setOnClickListener {
            onWordRemoveClick(element, adapterPosition)
        }
    }
}