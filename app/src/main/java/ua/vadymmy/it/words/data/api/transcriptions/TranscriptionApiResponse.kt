package ua.vadymmy.it.words.data.api.transcriptions

data class TranscriptionApiEntry(val all: String)

data class TranscriptionApiResponse(
    val word: String,
    val pronunciation: TranscriptionApiEntry,
    val success: Boolean = true,
    val message: String?
)