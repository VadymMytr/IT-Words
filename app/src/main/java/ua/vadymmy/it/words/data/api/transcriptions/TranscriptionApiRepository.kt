package ua.vadymmy.it.words.data.api.transcriptions

import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.transcription.TranscriptionRepository

class TranscriptionApiRepository @Inject constructor(
    private val transcriptionApi: TranscriptionApi
) : TranscriptionRepository {

    private companion object {
        private const val HYPHEN = "-"
        private const val SPACE = " "
        private const val EMPTY = ""
    }

    override suspend fun getTranscriptionFor(wordOriginal: String): String {
        val transcriptionBuilder = buildString {
            wordOriginal.split(HYPHEN, SPACE).forEach {
                append(
                    try {
                        val text = transcriptionApi.getPronunciationOf(it).mapToTranscription()

                        //remove redundant symbols
                        text.replace("[", EMPTY)
                            .replace("]", EMPTY)
                            .replace("/", EMPTY).let { result ->
                                if (result.isNotEmpty()) result.plus(SPACE)
                                else result
                            }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        EMPTY
                    }
                )
            }
        }

        return transcriptionBuilder.takeIf { it.isNotEmpty() }
            ?.removeSuffix(SPACE)
            ?.let { return@let "[$it]" }
            ?: EMPTY
    }
}