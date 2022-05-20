package ua.vadymmy.it.words.domain.entities

import ua.vadymmy.it.words.utils.newUUID

data class Word(
    val original: String,
    val translate: String,
    val transcription: String,
    val image: WordImage = WordImage.empty,
    var complaintsAmount: Int = NO_COMPLAINTS,
    val isAddedByUser: Boolean = IS_ADDED_BY_USER,
    val uuid: String = newUUID,
) {
    fun isCorrectTranslate(translate: String) = this.translate.lowercase() == translate.lowercase()
    fun isCorrectAnswer(answer: String) = this.original.lowercase() == answer.lowercase()
    val answerRequiredLength get() = original.length

    private companion object {
        private const val NO_COMPLAINTS = 0
        private const val IS_ADDED_BY_USER = false
    }
}