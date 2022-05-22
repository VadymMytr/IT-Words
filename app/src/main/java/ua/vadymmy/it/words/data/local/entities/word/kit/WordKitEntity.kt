package ua.vadymmy.it.words.data.local.entities.word.kit

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.models.word.common.WordImage
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Entity(tableName = "Word_Kits")
open class WordKitEntity(
    @PrimaryKey
    var word_kit_uuid: String,
    var word_kit_name: String,
    var word_kit_category_name: String,
    @Embedded(prefix = "word_kit_image_")
    var word_kit_image: WordImage
) {

    constructor() : this("", "", "", WordImage.empty)

    constructor(wordKit: WordKit) : this(
        wordKit.uuid,
        wordKit.name,
        wordKit.category.name,
        wordKit.image
    )
}