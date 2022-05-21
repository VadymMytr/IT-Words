package ua.vadymmy.it.words.domain.words.creations

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.images.GetWordImageByOriginalUseCase
import ua.vadymmy.it.words.domain.api.transcription.GetWordTranscriptionUseCase
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordParameters

@Reusable
class CreateWordFromParamsUseCase @Inject constructor(
    private val imageByOriginalUseCase: GetWordImageByOriginalUseCase,
    private val transcriptionUseCase: GetWordTranscriptionUseCase
) : BaseUseCase<WordParameters, Word>() {

    override suspend fun execute(request: WordParameters): Word {
        val image = imageByOriginalUseCase(request.original)
        val transcription = transcriptionUseCase(request.original)

        return Word(
            original = request.original,
            translate = request.translate,
            transcription = transcription,
            image = image,
            isAddedByUser = true
        )
    }
}