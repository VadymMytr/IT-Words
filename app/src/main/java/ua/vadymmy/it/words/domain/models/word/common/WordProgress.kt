package ua.vadymmy.it.words.domain.models.word.common

sealed class WordProgress {
    object None : WordProgress()
    object Learned : WordProgress()
    class InProgress(val progress: Int) : WordProgress()

    companion object {
        const val NONE = 0
        const val LEARNED = 3
        private val inProgressRange = IntRange(NONE.inc(), LEARNED.dec())

        fun from(progress: Int) = when (progress) {
            NONE -> None
            LEARNED -> Learned
            in inProgressRange -> InProgress(progress)

            else -> None
        }
    }
}