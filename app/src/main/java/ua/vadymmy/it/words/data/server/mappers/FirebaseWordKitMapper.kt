package ua.vadymmy.it.words.data.server.mappers

import com.google.firebase.firestore.DocumentSnapshot
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitCategory
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitInfo
import ua.vadymmy.it.words.utils.findString
import ua.vadymmy.it.words.utils.findStringList
import ua.vadymmy.it.words.utils.serialize

private const val KEY_WORDS_UUIDS = "kit_words_uuids"
private const val KEY_NAME = "kit_name"
private const val KEY_IMAGE_URL = "kit_image_url"
private const val KEY_IMAGE_COLOR = "kit_image_color"
private const val KEY_CATEGORY_NAME = "kit_category_name"

private val WordKit.wordsUUIDs get() = words.map { it.uuid }.serialize()

fun WordKit.mapToHashMap(): HashMap<String, Any> = hashMapOf(
    KEY_WORDS_UUIDS to wordsUUIDs,
    KEY_NAME to info.name,
    KEY_IMAGE_URL to info.image.url,
    KEY_IMAGE_COLOR to info.image.colorHex,
    KEY_CATEGORY_NAME to info.category.name
)

val WordKit.wordsUUIDsHashMap get() = hashMapOf(KEY_WORDS_UUIDS to wordsUUIDs)

val DocumentSnapshot.wordsUUIDs get() = findStringList(KEY_WORDS_UUIDS)

fun DocumentSnapshot.mapToWordKitInfo(): WordKitInfo = WordKitInfo(
    uuid = id,
    name = findString(KEY_NAME),
    image = WordImage(findString(KEY_IMAGE_URL), findString(KEY_IMAGE_COLOR)),
    category = WordKitCategory.valueOf(findString(KEY_CATEGORY_NAME))
)

fun DocumentSnapshot.mapToWordKit(words: List<Word>): WordKit = WordKit(
    info = mapToWordKitInfo(),
    words = words
)