package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.utils.newUUID

data class WordKitInfo(
    val name: String,
    val image: WordImage,
    val category: WordKitCategory,
    val uuid: String = newUUID
)