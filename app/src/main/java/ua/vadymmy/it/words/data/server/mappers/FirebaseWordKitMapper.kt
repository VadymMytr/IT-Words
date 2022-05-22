package ua.vadymmy.it.words.data.server.mappers

import com.google.firebase.firestore.DocumentSnapshot
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitCategory
import ua.vadymmy.it.words.utils.findString
import ua.vadymmy.it.words.utils.findStringList
import ua.vadymmy.it.words.utils.serialize

private const val KEY_WORDS_UUIDS = "kit_words_uuids"
private const val KEY_NAME = "kit_name"
private const val KEY_IMAGE_URL = "kit_image_url"
private const val KEY_IMAGE_COLOR = "kit_image_color"
private const val KEY_CATEGORY_NAME = "kit_category_name"
const val KEY_PREDEFINED_UUID = "learn_kit_predefined_uuid"

private val WordKit.wordsUUIDs get() = words.map { it.uuid }.serialize()
fun WordKit.toWordsUUIDHashMap() = hashMapOf<String, Any>(KEY_WORDS_UUIDS to wordsUUIDs)

fun WordKit.mapToHashMap() = hashMapOf<String, Any>(
    KEY_NAME to name,
    KEY_IMAGE_URL to image.url,
    KEY_IMAGE_COLOR to image.colorHex,
    KEY_CATEGORY_NAME to category.name
).apply {
    putAll(toWordsUUIDHashMap())
}

fun LearningWordKit.mapToLearningHashMap() = hashMapOf<String, Any>(
    KEY_PREDEFINED_UUID to predefinedKitUUID
).apply {
    putAll(mapToHashMap())
}

val DocumentSnapshot.wordsUUIDs get() = findStringList(KEY_WORDS_UUIDS)

fun DocumentSnapshot.mapToWordKit(words: List<Word>) = WordKit(
    uuid = id,
    name = findString(KEY_NAME),
    image = WordImage(findString(KEY_IMAGE_URL), findString(KEY_IMAGE_COLOR)),
    category = WordKitCategory.valueOf(findString(KEY_CATEGORY_NAME)),
    words = words.toMutableList()
)

fun DocumentSnapshot.mapToLearningWordKit(words: List<Word>) = LearningWordKit(
    wordKit = mapToWordKit(words),
    predefinedKitUUID = findString(KEY_PREDEFINED_UUID),
    isFullCopy = true
)