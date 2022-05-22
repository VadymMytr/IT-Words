package ua.vadymmy.it.words.domain.entities.word.common

import ua.vadymmy.it.words.data.local.entities.word.WordEntity
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

    constructor(wordEntity: WordEntity, progress: Int = WordProgress.NONE) : this(
        original = wordEntity.word_original,
        translate = wordEntity.word_translate,
        transcription = wordEntity.word_transcription,
        image = wordEntity.word_image,
        progress = progress,
        complaintsAmount = wordEntity.word_complaints_amount,
        isAddedByUser = wordEntity.word_is_added_by_user,
        uuid = wordEntity.word_uuid
    )

    private companion object {
        private const val NO_COMPLAINTS = 0
        private const val IS_ADDED_BY_USER = false
    }
}