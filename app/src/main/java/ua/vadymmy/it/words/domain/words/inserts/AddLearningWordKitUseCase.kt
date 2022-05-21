package ua.vadymmy.it.words.domain.words.inserts

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

@Reusable
class AddLearningWordKitUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<WordKit, Unit>() {

    override suspend fun execute(request: WordKit) {
        localRepository.addLearningWordKit(request)
        serverRepository.addLearningWordKit(request)
    }
}