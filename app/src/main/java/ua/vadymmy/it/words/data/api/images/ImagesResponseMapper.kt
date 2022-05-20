package ua.vadymmy.it.words.data.api.images

import ua.vadymmy.it.words.domain.entities.WordImage

fun List<ImageApiEntry>.mapToWordImages(): List<WordImage> =
    if (isNotEmpty()) map { it.mapToWordImage() }
    else listOf(WordImage.empty)

fun ImageApiEntry?.mapToWordImage() = this?.let {
    WordImage(contentUrl, "#$accentColor")
} ?: WordImage.empty