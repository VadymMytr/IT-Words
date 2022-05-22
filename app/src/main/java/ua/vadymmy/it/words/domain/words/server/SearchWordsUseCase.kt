package ua.vadymmy.it.words.domain.words.server

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.search.SearchParameters

@Reusable
class SearchWordsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) : BackgroundUseCase<SearchParameters, List<Word>>() {

    override suspend fun execute(request: SearchParameters): List<Word> {
        return serverRepository.searchWords(request)
    }
}