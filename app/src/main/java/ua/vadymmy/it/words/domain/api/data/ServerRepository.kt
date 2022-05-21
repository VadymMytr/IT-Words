package ua.vadymmy.it.words.domain.api.data

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.kit.WordKit

interface ServerRepository : DataRepository {
    suspend fun addWord(word: Word)
    suspend fun addComplaintOn(word: Word)
    suspend fun addPredefinedWordKit(wordKit: WordKit)// todo remove after loading all kits to firebase
    suspend fun getPredefinedKits(): List<WordKit>
}