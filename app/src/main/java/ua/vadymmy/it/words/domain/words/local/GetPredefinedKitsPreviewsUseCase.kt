package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Reusable
class GetPredefinedKitsPreviewsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<Unit, List<WordKit>>() {

    override suspend fun execute(request: Unit): List<WordKit> {
        return localRepository.getPredefinedWordKitsPreviews().sortedBy { it.category }
    }
}