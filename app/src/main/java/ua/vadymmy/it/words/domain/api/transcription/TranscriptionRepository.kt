package ua.vadymmy.it.words.domain.api.transcription

interface TranscriptionRepository {

    suspend fun getTranscriptionFor(wordOriginal: String): String
}