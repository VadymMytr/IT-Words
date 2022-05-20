package ua.vadymmy.it.words.data.api.transcriptions

private const val EMPTY_TRANSCRIPTION = ""

fun TranscriptionApiResponse.mapToTranscription(): String {
    message ?: return pronunciation.all
    return EMPTY_TRANSCRIPTION
}