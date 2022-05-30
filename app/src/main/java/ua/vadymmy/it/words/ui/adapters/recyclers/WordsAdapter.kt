package ua.vadymmy.it.words.ui.adapters.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import ua.vadymmy.it.words.databinding.ItemWordBinding
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.InProgress
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.Learned
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.None
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.ui.common.BaseAdapter

open class WordsAdapter(
    private val onSpeakWordClick: (Word) -> Unit,
    private val onWordRemoveClick: (Word, adapterPosition: Int) -> Unit = { _, _ -> }
) : BaseAdapter<ItemWordBinding, Word>() {

    private var isLearningKit: Boolean = false

    open fun setWords(wordKit: WordKit) {
        elements = wordKit.words.toMutableList()
        isLearningKit = false
    }

    open fun setWords(wordKit: LearningWordKit) {
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

        updateWordButtons(this, element, adapterPosition)
    }

    @CallSuper
    open fun updateWordButtons(binding: ItemWordBinding, element: Word, adapterPosition: Int) {
        with(binding) {
            wordItemDeleteButton.isVisible = isLearningKit
            wordItemDeleteButton.setOnClickListener {
                onWordRemoveClick(element, adapterPosition)
            }

            wordItemSpeakButton.setOnClickListener {
                onSpeakWordClick(element)
                wordItemSpeakButton.playAnimation()
            }
        }
    }
}