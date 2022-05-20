package ua.vadymmy.it.words.data.api.images

import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.repositories.ImageApiRepository
import ua.vadymmy.it.words.domain.entities.WordImage

class ImageRepository @Inject constructor(
    private val imageApi: ImageApi,
    private val imagesResponseMapper: ImagesResponseMapper
) : ImageApiRepository {

    companion object {
        private const val ONE_IMAGE = 1
    }

    private suspend fun processQuery(query: String, count: Int): ImageApiResponse {
        return imageApi.findImage(query, count)
    }

    override suspend fun findImage(query: String): WordImage {
        with(imagesResponseMapper) {
            return processQuery(query, ONE_IMAGE).firstEntry.mapToWordImage()
        }
    }

    override suspend fun findImages(query: String, count: Int): List<WordImage> {
        with(imagesResponseMapper) {
            return processQuery(query, count).mapToWordImages()
        }
    }
}