package ua.vadymmy.it.words.data.local.entities.word.kit

import androidx.room.Entity
import ua.vadymmy.it.words.domain.models.word.common.WordImage
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit

@Entity(tableName = "Learning_Word_Kits")
class LearningWordKitEntity(
    learning_word_kit_uuid: String,
    learning_word_kit_name: String,
    learning_word_kit_category_name: String,
    learning_word_kit_image: WordImage,
    var learning_word_kit_predefined_uuid: String
) : WordKitEntity(
    learning_word_kit_uuid,
    learning_word_kit_name,
    learning_word_kit_category_name,
    learning_word_kit_image
) {

    constructor() : this("", "", "", WordImage.empty, "")

    constructor(learningWordKit: LearningWordKit) : this(
        learningWordKit.uuid,
        learningWordKit.name,
        learningWordKit.category.name,
        learningWordKit.image,
        learningWordKit.predefinedKitUUID
    )
}