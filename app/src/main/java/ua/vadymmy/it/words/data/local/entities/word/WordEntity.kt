package ua.vadymmy.it.words.data.local.entities.word

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage

@Entity(tableName = "Words")
data class WordEntity(
    @PrimaryKey
    val word_uuid: String,
    val word_original: String,
    val word_translate: String,
    val word_transcription: String,
    @Embedded(prefix = "word_image_")
    val word_image: WordImage,
    val word_complaints_amount: Int,
    val word_is_added_by_user: Boolean
) {

    constructor(word: Word) : this(
        word.uuid,
        word.original,
        word.translate,
        word.transcription,
        word.image,
        word.complaintsAmount,
        word.isAddedByUser
    )
}