package ua.vadymmy.it.words.ui.adapters.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import ua.vadymmy.it.words.R
import ua.vadymmy.it.words.databinding.ItemLearningKitPreviewBinding
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.ui.common.BaseAdapter
import ua.vadymmy.it.words.utils.loadFrom

class LearningKitsPreviewsAdapter(
    private val onKitClick: (LearningWordKit) -> Unit,
    private val onKitRemoveClick: (LearningWordKit, adapterPosition: Int) -> Unit
) : BaseAdapter<ItemLearningKitPreviewBinding, LearningWordKit>() {

    override fun inflateBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemLearningKitPreviewBinding.inflate(layoutInflater, parent, false)

    override fun ItemLearningKitPreviewBinding.onBindElement(
        element: LearningWordKit,
        context: Context,
        adapterPosition: Int
    ) {
        learningKitPreviewImage.loadFrom(element.image)
        learningKitPreviewName.text = element.name
        learningKitPreviewCategory.setText(element.category.titleRes)

        val isLearned = element.isFullyLearned
        learningKitPreviewDoneImage.isVisible = isLearned
        learningKitPreviewProgressLayout.isVisible = !isLearned
        if (!isLearned) {
            learningKitPreviewProgressBar.progress = element.learnProgressPercent
            learningKitPreviewProgressText.text = context.getString(
                R.string.kit_progress_placeholder,
                element.learnProgress,
                element.size
            )
        }

        learningKitPreviewImage.setOnClickListener {
            onKitClick(element)
        }

        root.setOnClickListener {
            onKitClick(element)
        }

        learningKitPreviewDeleteButton.setOnClickListener {
            onKitRemoveClick(element, adapterPosition)
        }
    }
}