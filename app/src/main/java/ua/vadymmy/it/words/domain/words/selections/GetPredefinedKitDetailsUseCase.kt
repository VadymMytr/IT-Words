package ua.vadymmy.it.words.domain.words.selections

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitInfo

@Reusable
class GetPredefinedKitDetailsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BackgroundUseCase<WordKitInfo, WordKit>() {

    override suspend fun execute(request: WordKitInfo): WordKit {
        return serverRepository.getPredefinedKitDetails(request.uuid)
    }
}