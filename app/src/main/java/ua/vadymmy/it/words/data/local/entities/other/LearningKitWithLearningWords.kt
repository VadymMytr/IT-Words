package ua.vadymmy.it.words.data.local.entities.other

import androidx.room.ColumnInfo
import androidx.room.Embedded
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity

data class LearningKitWithLearningWords(
    @Embedded var learningWordKitEntity: LearningWordKitEntity,
    @Embedded var wordEntity: WordEntity,
    @ColumnInfo(name = "users_learning_words_word_progress")
    var progress: Int
)