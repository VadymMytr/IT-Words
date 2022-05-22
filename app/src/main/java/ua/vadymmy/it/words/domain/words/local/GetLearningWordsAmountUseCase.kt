package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase

@Reusable
class GetLearningWordsAmountUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<Unit, Int>() {

    override suspend fun execute(request: Unit): Int {
        return localRepository.getLearningWordsAmount()
    }
}