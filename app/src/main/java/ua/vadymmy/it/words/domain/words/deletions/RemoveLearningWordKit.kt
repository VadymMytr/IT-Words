package ua.vadymmy.it.words.domain.words.deletions

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit

@Reusable
class RemoveLearningWordKit @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<LearningWordKit, Unit>() {

    override suspend fun execute(request: LearningWordKit) {
        localRepository.removeLearningWordKit(request)
        serverRepository.removeLearningWordKit(request)
    }
}