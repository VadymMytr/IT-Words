package ua.vadymmy.it.words.data.api.images

import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.images.ImageRepository
import ua.vadymmy.it.words.domain.entities.WordImage

class ImageApiRepository @Inject constructor(private val imageApi: ImageApi) : ImageRepository {

    companion object {
        private const val ONE_IMAGE = 1
    }

    private suspend fun processQuery(query: String, count: Int): List<ImageApiEntry> =
        try {
            imageApi.findImage(query, count).value
        } catch (ex: Exception) {
            ex.printStackTrace()
            listOf()
        }

    override suspend fun findImage(query: String): WordImage {
        return processQuery(query, ONE_IMAGE).firstOrNull().mapToWordImage()
    }

    override suspend fun findImages(query: String, count: Int): List<WordImage> {
        return processQuery(query, count).mapToWordImages()
    }
}