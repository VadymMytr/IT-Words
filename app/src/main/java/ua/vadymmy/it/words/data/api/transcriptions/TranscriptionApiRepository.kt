package ua.vadymmy.it.words.data.api.transcriptions

import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.transcription.TranscriptionRepository

class TranscriptionApiRepository @Inject constructor(
    private val transcriptionApi: TranscriptionApi
) : TranscriptionRepository {

    private companion object {
        private const val HYPHEN = "-"
        private const val SPACE = " "
    }

    override suspend fun getTranscriptionFor(wordOriginal: String): String {
        val transcriptionBuilder = buildString {
            wordOriginal.split(HYPHEN, SPACE).forEach {
                append(
                    try {
                        transcriptionApi.getPronunciationOf(it).mapToTranscription().plus(SPACE)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        SPACE
                    }
                )
            }
        }

        return "[${transcriptionBuilder.removeSuffix(SPACE)}]"
    }
}