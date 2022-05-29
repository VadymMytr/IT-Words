package ua.vadymmy.it.words.domain.words.utils

import android.speech.tts.TextToSpeech
import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.utils.speak

@Reusable
class PronounceWordUseCase @Inject constructor(
    private val textToSpeech: TextToSpeech
) : BaseUseCase<Word, Unit>() {

    override suspend fun execute(request: Word) {
        textToSpeech.speak(request)
    }
}