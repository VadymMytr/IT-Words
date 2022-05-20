package ua.vadymmy.it.words.data.api.transcriptions

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TranscriptionApi {

    private companion object {
        private const val PRONUNCIATION_API_HOST = "wordsapiv1.p.rapidapi.com"
        private const val API_KEY = "cc322d6c92mshc82d4eb9d1201cfp13d844jsn96c3a566c378"
    }

    @GET("words/{word}/pronunciation")
    @Headers("X-RapidAPI-Host: $PRONUNCIATION_API_HOST", "X-RapidAPI-Key: $API_KEY")
    suspend fun getPronunciationOf(
        @Path(value = "word", encoded = true) word: String
    ): TranscriptionApiResponse
}