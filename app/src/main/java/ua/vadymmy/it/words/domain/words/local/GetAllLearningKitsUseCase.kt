package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit

@Reusable
class GetAllLearningKitsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<Unit, List<LearningWordKit>>() {

    override suspend fun execute(request: Unit): List<LearningWordKit> {
        return localRepository.getLearningWordKits().sortedBy { it.category }
    }
}