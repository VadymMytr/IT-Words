package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Entity(
    tableName = "PredefinedKits_Words",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["word_uuid"],
            childColumns = ["pred_kits_words_word_uuid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PredefinedWordKitEntity::class,
            parentColumns = ["word_kit_uuid"],
            childColumns = ["pred_kits_words_kit_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PredefinedKitsWordsEntity(
    @PrimaryKey(autoGenerate = true)
    var pred_kits_words_id: Int = 0,
    var pred_kits_words_word_uuid: String,
    var pred_kits_words_kit_uuid: String
) {

    constructor(kit: WordKit, word: WordEntity) : this(
        pred_kits_words_word_uuid = word.word_uuid,
        pred_kits_words_kit_uuid = kit.uuid
    )
}