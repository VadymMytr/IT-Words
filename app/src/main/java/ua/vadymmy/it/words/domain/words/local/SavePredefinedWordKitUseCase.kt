package ua.vadymmy.it.words.domain.words.local

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Reusable
class SavePredefinedWordKitUseCase @Inject constructor(
    private val localRepository: LocalRepository
) : BackgroundUseCase<WordKit, Unit>() {

    override suspend fun execute(request: WordKit) {
        localRepository.addPredefinedWordKit(request)
    }
}