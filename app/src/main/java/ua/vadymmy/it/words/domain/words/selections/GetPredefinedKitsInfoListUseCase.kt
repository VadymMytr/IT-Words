package ua.vadymmy.it.words.domain.words.selections

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitInfo

@Reusable
class GetPredefinedKitsInfoListUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BaseUseCase<Unit, List<WordKitInfo>>() {

    override suspend fun execute(request: Unit): List<WordKitInfo> {
        return serverRepository.getPredefinedKitsInfo()
    }
}