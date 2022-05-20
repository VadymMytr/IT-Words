package ua.vadymmy.it.words.data.api.images

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageApi {

    private companion object {
        private const val IMAGE_API_HOST = "bing-image-search1.p.rapidapi.com"
        private const val API_KEY = "cc322d6c92mshc82d4eb9d1201cfp13d844jsn96c3a566c378"
    }

    @GET("images/search")
    @Headers("X-RapidAPI-Host: $IMAGE_API_HOST", "X-RapidAPI-Key: $API_KEY")
    suspend fun findImage(
        @Query("q") query: String,
        @Query("count") count: Int
    ): ImageApiResponse
}
