package ua.vadymmy.it.words.domain.words.other

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.common.WordParameters

@Reusable
class CheckWordExistsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BackgroundUseCase<WordParameters, Boolean>() {

    override suspend fun execute(request: WordParameters): Boolean {
        return serverRepository.isWordExists(request)
    }
}