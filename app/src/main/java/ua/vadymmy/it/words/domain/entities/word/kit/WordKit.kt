package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.common.WordProgress.Learned
import ua.vadymmy.it.words.utils.newUUID

data class WordKit(
    val words: List<Word>,
    val name: String,
    val image: WordImage,
    val category: WordKitCategory,
    val uuid: String = newUUID
) {

    val size get() = words.size
    val progress get() = words.count { it.wordProgress is Learned }
}