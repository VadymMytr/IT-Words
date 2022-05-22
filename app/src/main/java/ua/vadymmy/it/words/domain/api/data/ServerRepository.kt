package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.common.WordParameters
import ua.vadymmy.it.words.domain.models.word.kit.WordKit
import ua.vadymmy.it.words.domain.models.word.search.SearchParameters

interface ServerRepository : DataRepository {
    suspend fun addWord(word: Word)
    suspend fun getPredefinedWordKits(): List<WordKit>
    suspend fun isWordExists(wordParameters: WordParameters): Boolean
    suspend fun searchWords(searchParameters: SearchParameters): List<Word>
}