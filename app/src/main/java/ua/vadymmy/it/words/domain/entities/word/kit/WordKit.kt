package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.utils.newUUID

open class WordKit(
    var name: String,
    val image: WordImage,
    val category: WordKitCategory,
    val words: MutableList<Word>,
    val uuid: String = newUUID
) {
    val size get() = words.size

    constructor(
        wordKit: PredefinedWordKitEntity,
        words: MutableList<Word> = mutableListOf()
    ) : this(
        name = wordKit.word_kit_name,
        image = wordKit.word_kit_image,
        category = WordKitCategory.valueOf(wordKit.word_kit_category_name),
        words = words,
        uuid = wordKit.word_kit_uuid
    )
}