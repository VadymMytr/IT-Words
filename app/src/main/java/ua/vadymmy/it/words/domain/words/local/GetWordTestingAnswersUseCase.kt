package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.common.Word

@Reusable
class GetWordTestingAnswersUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<Word, List<Word>>() {

    override suspend fun execute(request: Word): List<Word> {
        return localRepository.getRandomWords(OTHER_TEST_VARIANTS_AMOUNT)
    }

    companion object {
        private const val OTHER_TEST_VARIANTS_AMOUNT = 2
    }
}