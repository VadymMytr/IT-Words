package ua.vadymmy.it.words.domain.words.both

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.common.Word

@Reusable
class GetWordTestingAnswersUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<Word, List<Word>>() {

    override suspend fun execute(request: Word): List<Word> {
        var randomWords = serverRepository.getRandomWords(OTHER_TEST_VARIANTS_AMOUNT)
        if (randomWords.isEmpty()) {
            randomWords = localRepository.getRandomWords(OTHER_TEST_VARIANTS_AMOUNT)
        }

        return mutableListOf(request).apply { addAll(randomWords) }
    }

    companion object {
        private const val OTHER_TEST_VARIANTS_AMOUNT = 2
    }
}