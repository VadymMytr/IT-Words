package ua.vadymmy.it.words.data.local.entities.word.kit

import androidx.room.Entity
import ua.vadymmy.it.words.domain.models.word.common.WordImage
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Entity(tableName = "Predefined_Word_Kits")
class PredefinedWordKitEntity(
    predefined_word_kit_uuid: String,
    predefined_word_kit_name: String,
    predefined_word_kit_category_name: String,
    predefined_word_kit_image: WordImage,
) : WordKitEntity(
    predefined_word_kit_uuid,
    predefined_word_kit_name,
    predefined_word_kit_category_name,
    predefined_word_kit_image
) {

    constructor() : this("", "", "", WordImage.empty)

    constructor(wordKit: WordKit) : this(
        wordKit.uuid,
        wordKit.name,
        wordKit.category.name,
        wordKit.image,
    )
}