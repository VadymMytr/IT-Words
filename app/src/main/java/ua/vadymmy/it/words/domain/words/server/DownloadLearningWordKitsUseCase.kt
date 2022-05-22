package ua.vadymmy.it.words.domain.words.server

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit

@Reusable
class DownloadLearningWordKitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BaseUseCase<Unit, List<LearningWordKit>>() {

    override suspend fun execute(request: Unit): List<LearningWordKit> {
        return serverRepository.getLearningWordKits()
    }
}