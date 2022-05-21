package ua.vadymmy.it.words.data.local.entities.word.kit

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

@Entity(tableName = "Word_Kits")
open class WordKitEntity(
    @PrimaryKey
    val word_kit_uuid: String,
    val word_kit_name: String,
    val word_kit_category_name: String,
    @Embedded(prefix = "word_kit_image_")
    val word_kit_image: WordImage
) {

    constructor(wordKit: WordKit) : this(
        wordKit.uuid,
        wordKit.name,
        wordKit.category.name,
        wordKit.image
    )
}