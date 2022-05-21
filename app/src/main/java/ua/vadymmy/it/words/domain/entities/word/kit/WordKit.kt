package ua.vadymmy.it.words.domain.entities.word.kit

import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.common.WordProgress.Learned

data class WordKit(
    val info: WordKitInfo,
    val words: List<Word>
) {
    val size get() = words.size
    val progress get() = words.count { it.wordProgress is Learned }
    val isFullyLearned get() = size == progress
}