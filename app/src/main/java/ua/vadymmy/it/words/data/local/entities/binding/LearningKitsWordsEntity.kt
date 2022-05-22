package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.domain.models.word.common.Word
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit

@Entity(
    tableName = "LearningKits_Words",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["word_uuid"],
            childColumns = ["learn_kits_words_word_uuid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LearningWordKitEntity::class,
            parentColumns = ["word_kit_uuid"],
            childColumns = ["learn_kits_words_kit_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LearningKitsWordsEntity(
    @PrimaryKey(autoGenerate = true)
    var learn_kits_words_id: Int = 0,
    var learn_kits_words_word_uuid: String,
    var learn_kits_words_kit_uuid: String
) {

    constructor(kitUUID: String, word: Word) : this(
        learn_kits_words_word_uuid = word.uuid,
        learn_kits_words_kit_uuid = kitUUID
    )

    constructor(learningWordKit: LearningWordKit, word: Word) : this(learningWordKit.uuid, word)
}