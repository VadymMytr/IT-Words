package ua.vadymmy.it.words.data.local.entities.word.kit

import androidx.room.Entity
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit

@Entity(tableName = "Learning_Word_Kits")
class LearningWordKitEntity(
    learning_word_kit_uuid: String,
    learning_word_kit_name: String,
    learning_word_kit_category_name: String,
    learning_word_kit_image: WordImage,
    private val learning_word_kit_predefined_uuid: String
) : WordKitEntity(
    learning_word_kit_uuid,
    learning_word_kit_name,
    learning_word_kit_category_name,
    learning_word_kit_image
) {

    constructor(learningWordKit: LearningWordKit) : this(
        learningWordKit.uuid,
        learningWordKit.name,
        learningWordKit.category.name,
        learningWordKit.image,
        learningWordKit.predefinedKitUUID
    )
}