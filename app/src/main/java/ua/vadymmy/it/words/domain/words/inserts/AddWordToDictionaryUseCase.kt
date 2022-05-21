package ua.vadymmy.it.words.domain.words.inserts

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BaseUseCase
import ua.vadymmy.it.words.domain.entities.word.common.Word

@Reusable
class AddWordToDictionaryUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BaseUseCase<Word, Unit>() {

    override suspend fun execute(request: Word) {
        serverRepository.addWord(request)
    }
}