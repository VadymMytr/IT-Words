package ua.vadymmy.it.words.data.api.images

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageApi {

    private companion object {
        private const val IMAGE_API_HOST = "bing-image-search1.p.rapidapi.com"
        private const val IMAGE_API_KEY = "cc322d6c92mshc82d4eb9d1201cfp13d844jsn96c3a566c378"
    }

    @FormUrlEncoded
    @GET("images/search")
    @Headers("X-RapidAPI-Host: $IMAGE_API_HOST", "X-RapidAPI-Key: $IMAGE_API_KEY")
    suspend fun findImage(
        @Field("q") query: String,
        @Field("count") count: Int
    ): ImageApiResponse
}
