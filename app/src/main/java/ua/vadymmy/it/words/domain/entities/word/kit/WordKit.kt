package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.utils.newUUID

open class WordKit(
    var name: String,
    val image: WordImage,
    val category: WordKitCategory,
    val words: MutableList<Word>,
    val uuid: String = newUUID
){
    val size get() = words.size
}