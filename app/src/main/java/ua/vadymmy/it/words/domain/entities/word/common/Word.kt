package ua.vadymmy.it.words.domain.entities.word.common

import ua.vadymmy.it.words.utils.newUUID

open class Word(
    val original: String,
    val translate: String,
    val transcription: String,
    val image: WordImage,
    var progress: Int = WordProgress.NONE,
    var complaintsAmount: Int = NO_COMPLAINTS,
    val isAddedByUser: Boolean = IS_ADDED_BY_USER,
    val uuid: String = newUUID
) {
    val wordProgress: WordProgress
        get() = WordProgress.from(progress)

    fun isCorrectTranslate(translate: String) = translate.lowercase() == translate.lowercase()
    fun isCorrectAnswer(answer: String) = original.lowercase() == answer.lowercase()
    val answerRequiredLength get() = original.length

    private companion object {
        private const val NO_COMPLAINTS = 0
        private const val IS_ADDED_BY_USER = false
    }
}