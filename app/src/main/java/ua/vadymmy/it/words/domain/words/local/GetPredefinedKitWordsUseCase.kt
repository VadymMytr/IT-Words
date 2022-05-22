package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.common.Word

@Reusable
class GetPredefinedKitWordsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<String, List<Word>>() {

    override suspend fun execute(request: String): List<Word> {
        return localRepository.getPredefinedWordKitWords(kitUUID = request)
    }
}