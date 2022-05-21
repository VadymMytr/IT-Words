package ua.vadymmy.it.words.data.api.transcriptions

private const val EMPTY_TRANSCRIPTION = ""

fun List<TranscriptionApiResponse>.mapToTranscription(): String {
    return firstOrNull()?.phonetic ?: EMPTY_TRANSCRIPTION
}