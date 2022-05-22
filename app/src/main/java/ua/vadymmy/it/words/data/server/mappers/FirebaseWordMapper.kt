package ua.vadymmy.it.words.data.server.mappers

import com.google.firebase.firestore.DocumentSnapshot
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordImage
import ua.vadymmy.it.words.utils.findBoolean
import ua.vadymmy.it.words.utils.findInt
import ua.vadymmy.it.words.utils.findString

const val KEY_ORIGINAL = "word_original"
const val KEY_TRANSLATE = "word_translate"
private const val KEY_TRANSCRIPTION = "word_transcription"
private const val KEY_IMAGE_URL = "word_image_url"
private const val KEY_IMAGE_COLOR = "word_image_color"
private const val KEY_COMPLAINTS_AMOUNT = "word_complaints_amount"
private const val KEY_IS_ADDED_BY_USER = "word_is_added_by_user"
private const val KEY_WORD_PROGRESS = "learning_word_progress"

fun Word.mapToHashMap(): HashMap<String, Any> = hashMapOf(
    KEY_ORIGINAL to original,
    KEY_TRANSLATE to translate,
    KEY_TRANSCRIPTION to transcription,
    KEY_IMAGE_URL to image.url,
    KEY_IMAGE_COLOR to image.colorHex,
    KEY_COMPLAINTS_AMOUNT to complaintsAmount,
    KEY_IS_ADDED_BY_USER to isAddedByUser
)

val Word.complaintsHashMap get() = hashMapOf<String, Any>(KEY_COMPLAINTS_AMOUNT to complaintsAmount)
val Word.learningHashMap get() = hashMapOf<String, Any>(KEY_WORD_PROGRESS to progress)

val DocumentSnapshot.wordProgress get() = findInt(KEY_WORD_PROGRESS)
fun DocumentSnapshot.mapToWord(): Word = Word(
    uuid = id,
    original = findString(KEY_ORIGINAL),
    translate = findString(KEY_TRANSLATE),
    transcription = findString(KEY_TRANSCRIPTION),
    image = WordImage(findString(KEY_IMAGE_URL), findString(KEY_IMAGE_COLOR)),
    complaintsAmount = findInt(KEY_COMPLAINTS_AMOUNT),
    isAddedByUser = findBoolean(KEY_IS_ADDED_BY_USER)
)