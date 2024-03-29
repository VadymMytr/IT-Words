package ua.vadymmy.it.words.ui.adapters.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.allViews
import ua.vadymmy.it.words.databinding.ItemPredefinedKitPreviewBinding
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.ui.common.BaseAdapter
import ua.vadymmy.it.words.utils.loadFrom

class PredefinedKitsPreviewsAdapter(
    private val onKitClick: (WordKit) -> Unit
) : BaseAdapter<ItemPredefinedKitPreviewBinding, WordKit>() {

    override fun inflateBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = ItemPredefinedKitPreviewBinding.inflate(layoutInflater, parent, false)

    override fun ItemPredefinedKitPreviewBinding.onBindElement(
        element: WordKit,
        context: Context,
        adapterPosition: Int
    ) {
        predefinedKitPreviewImage.loadFrom(element.image)
        predefinedKitPreviewName.text = element.name
        predefinedKitPreviewCategory.setText(element.category.titleRes)
        root.allViews.forEach { it.setOnClickListener { onKitClick(element) } }
    }
}