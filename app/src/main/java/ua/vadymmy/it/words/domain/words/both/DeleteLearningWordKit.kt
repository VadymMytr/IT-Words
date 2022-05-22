package ua.vadymmy.it.words.domain.words.both

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit

@Reusable
class DeleteLearningWordKit @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<LearningWordKit, Unit>() {

    override suspend fun execute(request: LearningWordKit) {
        localRepository.removeLearningWordKit(request)
        serverRepository.removeLearningWordKit(request)
    }
}