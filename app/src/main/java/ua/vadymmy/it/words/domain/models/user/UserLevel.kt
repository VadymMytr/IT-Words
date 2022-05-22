package ua.vadymmy.it.words.domain.models.user

import androidx.annotation.StringRes
import ua.vadymmy.it.words.R

//TODO FILL TITLES
enum class UserLevel(@StringRes val titleRes: Int, private val rangeEnd: Int) {
    BEGINNER(R.string.app_name, 50),
    EXPLORER(R.string.app_name, 150),
    ENTHUSIAST(R.string.app_name, 300),
    HUNTER(R.string.app_name, 700),
    KING(R.string.app_name, 1000),
    EMPEROR(R.string.app_name, 1500);

    companion object {
        private const val FIRST_ITEM_ORDINAL = 0

        fun fromProgress(progress: Int) = when {
            progress > EMPEROR.rangeEnd -> EMPEROR
            progress < BEGINNER.rangeStart -> BEGINNER
            else -> values().find { it.wordsRange.contains(progress) } ?: BEGINNER
        }
    }

    private val wordsRange get() = IntRange(rangeStart, rangeEnd)
    private val isFirstItem get() = ordinal == FIRST_ITEM_ORDINAL
    private val previousLevel get() = values()[ordinal - 1]
    private val rangeStart: Int get() = if (isFirstItem) FIRST_ITEM_ORDINAL else previousLevel.rangeEnd.inc()
}