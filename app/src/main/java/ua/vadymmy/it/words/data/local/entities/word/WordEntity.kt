package ua.vadymmy.it.words.data.local.entities.word

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.common.WordImage

@Entity(tableName = "Words")
data class WordEntity(
    @PrimaryKey
    var word_uuid: String,
    var word_original: String,
    var word_translate: String,
    var word_transcription: String,
    @Embedded(prefix = "word_image_")
    var word_image: WordImage,
    var word_complaints_amount: Int,
    var word_is_added_by_user: Boolean
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