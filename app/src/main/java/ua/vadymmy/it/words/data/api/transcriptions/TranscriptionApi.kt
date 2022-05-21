package ua.vadymmy.it.words.data.api.transcriptions

import retrofit2.http.GET
import retrofit2.http.Path

interface TranscriptionApi {

    @GET("https://api.dictionaryapi.dev/api/v2/entries/en/{word}")
    suspend fun getPronunciationOf(
        @Path(value = "word", encoded = true) word: String
    ): List<TranscriptionApiResponse>
}