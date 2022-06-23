package ua.vadymmy.it.words.domain.models.word.common

sealed class WordProgress {
    object None : WordProgress()
    object Learned : WordProgress()
    class InProgress(val progress: Int) : WordProgress() {
        val testType get() = TestType.from(this)
    }

    enum class TestType(private val atProgress: Int) {
        EN_VOICE_UA_SELECT(atProgress = 0),
        UA_TRANSLATE_EN_SELECT(atProgress = 1),
        UA_TRANSLATE_EN_INPUT(atProgress = 2);

        companion object {
            fun from(inProgress: WordProgress) = try {
                values().find {
                    it.atProgress == (inProgress as InProgress).progress
                } ?: EN_VOICE_UA_SELECT
            } catch (ex: Exception) {
                EN_VOICE_UA_SELECT
            }
        }
    }

    companion object {
        const val NONE = 0
        private const val LEARNED = 3
        private val inProgressRange = IntRange(NONE.inc(), LEARNED.dec())

        fun from(progress: Int) = when (progress) {
            NONE -> None
            LEARNED -> Learned
            in inProgressRange -> InProgress(progress)

            else -> None
        }
    }
}