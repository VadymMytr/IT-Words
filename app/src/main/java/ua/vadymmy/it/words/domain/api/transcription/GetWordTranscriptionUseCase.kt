package ua.vadymmy.it.words.domain.api.transcription

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.common.BackgroundUseCase

@Reusable
class GetWordTranscriptionUseCase @Inject constructor(
    private val transcriptionRepository: TranscriptionRepository
) : BackgroundUseCase<String, String>() {

    override suspend fun execute(request: String): String {
        return transcriptionRepository.getTranscriptionFor(request)
    }
}