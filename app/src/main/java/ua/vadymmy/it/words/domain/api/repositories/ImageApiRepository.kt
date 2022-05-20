package ua.vadymmy.it.words.domain.api.repositories

import ua.vadymmy.it.words.domain.entities.WordImage

interface ImageApiRepository {

    suspend fun findImage(query: String) : WordImage

    suspend fun findImages(query: String, count: Int) : List<WordImage>
}