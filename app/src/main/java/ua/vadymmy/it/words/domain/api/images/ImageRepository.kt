package ua.vadymmy.it.words.domain.api.images

import ua.vadymmy.it.words.domain.entities.word.common.WordImage

interface ImageRepository {

    suspend fun findImage(query: String) : WordImage

    suspend fun findImages(query: String, count: Int) : List<WordImage>
}