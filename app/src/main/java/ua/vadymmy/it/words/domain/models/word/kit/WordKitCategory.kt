package ua.vadymmy.it.words.domain.models.word.kit

import androidx.annotation.StringRes
import java.io.Serializable
import ua.vadymmy.it.words.R

enum class WordKitCategory(@StringRes val titleRes: Int) : Serializable {
    DEFAULT(R.string.kit_category_default),
    ARCHITECTURE(R.string.kit_category_arch),
    PROGRAMMING(R.string.kit_category_programming),
    MATH(R.string.kit_category_math),
    OTHER(R.string.kit_category_other),
    CUSTOM(R.string.kit_category_custom)
}