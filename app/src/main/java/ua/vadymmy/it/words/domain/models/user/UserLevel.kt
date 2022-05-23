package ua.vadymmy.it.words.domain.models.user

import androidx.annotation.StringRes
import ua.vadymmy.it.words.R

enum class UserLevel(@StringRes val titleRes: Int, val levelMax: Int) {
    BEGINNER(R.string.user_level_beginner, 50),
    EXPLORER(R.string.user_level_explorer, 150),
    ENTHUSIAST(R.string.user_level_enthusiast, 300),
    HUNTER(R.string.user_level_hunter, 700),
    KING(R.string.user_level_king, 1000),
    EMPEROR(R.string.user_level_emperor, 1500);

    companion object {
        private const val FIRST_ITEM_ORDINAL = 0

        fun fromProgress(progress: Int) = when {
            progress > EMPEROR.levelMax -> EMPEROR
            progress < BEGINNER.rangeStart -> BEGINNER
            else -> values().find { it.wordsRange.contains(progress) } ?: BEGINNER
        }
    }

    private val wordsRange get() = IntRange(rangeStart, levelMax)
    private val isFirstItem get() = ordinal == FIRST_ITEM_ORDINAL
    private val previousLevel get() = values()[ordinal - 1]
    private val rangeStart: Int get() = if (isFirstItem) FIRST_ITEM_ORDINAL else previousLevel.levelMax.inc()
}