package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Reusable
class GetPredefinedKitWordsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<WordKit, List<Word>>() {

    override suspend fun execute(request: WordKit): List<Word> {
        return localRepository.getPredefinedWordKitWords(kitUUID = request.uuid)
    }
}