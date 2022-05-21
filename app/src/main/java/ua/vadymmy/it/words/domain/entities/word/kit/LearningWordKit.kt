package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.common.WordProgress.Learned
import ua.vadymmy.it.words.utils.newUUID

class LearningWordKit(
    name: String,
    image: WordImage,
    category: WordKitCategory,
    words: ArrayList<Word>,
    uuid: String = newUUID,
    val predefinedKitUUID: String = NOT_PREDEFINED
) : WordKit(
    name,
    image,
    category,
    words,
    uuid
) {
    val isFullyLearned get() = size == learnProgress
    val isPredefined get() = predefinedKitUUID.isNotEmpty()
    val learnProgress get() = words.count { it.wordProgress is Learned }

    constructor(wordKit: WordKit, predefinedKitUUID: String) : this(
        wordKit.name,
        wordKit.image,
        wordKit.category,
        ArrayList(wordKit.words),
        predefinedKitUUID = predefinedKitUUID
    )

    companion object {
        private const val NOT_PREDEFINED = ""
    }
}