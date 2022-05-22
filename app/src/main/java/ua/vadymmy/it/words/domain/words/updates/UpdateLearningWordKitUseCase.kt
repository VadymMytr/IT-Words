package ua.vadymmy.it.words.domain.words.updates

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit

@Reusable
class UpdateLearningWordKitUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<LearningWordKit, Unit>() {

    override suspend fun execute(request: LearningWordKit) {
        localRepository.updateLearningWordKit(request)
        serverRepository.updateLearningWordKit(request)
    }
}