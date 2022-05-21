package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordParameters
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit
import ua.vadymmy.it.words.domain.entities.word.kit.WordKitInfo
import ua.vadymmy.it.words.domain.entities.word.search.SearchParameters

interface ServerRepository : DataRepository {
    suspend fun addWord(word: Word)
    suspend fun addComplaintOn(word: Word)
    suspend fun getPredefinedKitsInfo(): List<WordKitInfo>
    suspend fun getPredefinedKitDetails(uuid: String): WordKit
    suspend fun isWordExists(wordParameters: WordParameters): Boolean
    suspend fun searchWords(searchParameters: SearchParameters): List<Word>
}