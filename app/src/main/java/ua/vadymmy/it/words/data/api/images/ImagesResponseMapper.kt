package ua.vadymmy.it.words.data.api.images

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.entities.WordImage

@Reusable
class ImagesResponseMapper @Inject constructor() {

    fun ImageApiResponse.mapToWordImages(): List<WordImage> =
        if (value.isNotEmpty()) value.map { it.mapToWordImage() }
        else listOf(WordImage.empty)

    fun ImageApiEntry?.mapToWordImage() = this?.let {
        WordImage(contentUrl, "#$accentColor")
    } ?: WordImage.empty

}