package ua.vadymmy.it.words.domain.models.word.kit

import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.common.WordImage
import ua.vadymmy.it.words.domain.models.word.common.WordProgress.Learned
import ua.vadymmy.it.words.utils.newUUID

class LearningWordKit(
    name: String,
    image: WordImage,
    category: WordKitCategory,
    words: MutableList<Word>,
    uuid: String = newUUID,
    val predefinedKitUUID: String = NOT_PREDEFINED
) : WordKit(
    name = name,
    image = image,
    category = category,
    words = words,
    uuid = uuid
) {
    val isFullyLearned get() = size == learnProgress
    val isPredefined get() = predefinedKitUUID.isNotEmpty()
    val learnProgress get() = words.count { it.wordProgress is Learned }
    val learnProgressPercent get() = (size * learnProgress / MAX_PROGRESS).toInt()

    constructor(wordKit: WordKit, predefinedKitUUID: String, isFullCopy: Boolean = false) : this(
        name = wordKit.name,
        image = wordKit.image,
        category = wordKit.category,
        words = wordKit.words,
        uuid = if (isFullCopy) wordKit.uuid else newUUID,
        predefinedKitUUID = predefinedKitUUID
    )

    constructor(
        learningWordKitEntity: LearningWordKitEntity,
        words: MutableList<Word> = mutableListOf()
    ) : this(
        name = learningWordKitEntity.word_kit_name,
        image = learningWordKitEntity.word_kit_image,
        category = WordKitCategory.valueOf(learningWordKitEntity.word_kit_category_name),
        words = words,
        uuid = learningWordKitEntity.word_kit_uuid,
        predefinedKitUUID = learningWordKitEntity.learning_word_kit_predefined_uuid
    )

    companion object {
        private const val NOT_PREDEFINED = ""
        private const val MAX_PROGRESS = 100f
    }
}