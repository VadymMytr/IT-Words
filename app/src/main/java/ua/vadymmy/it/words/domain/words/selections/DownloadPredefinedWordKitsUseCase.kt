package ua.vadymmy.it.words.domain.words.selections

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

@Reusable
class DownloadPredefinedWordKitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BaseUseCase<Unit, List<WordKit>>() {

    override suspend fun execute(request: Unit): List<WordKit> {
        return serverRepository.getPredefinedWordKits()
    }
}