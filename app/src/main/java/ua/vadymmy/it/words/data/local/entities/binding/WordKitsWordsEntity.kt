package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.WordKitEntity

@Entity(
    tableName = "Word_Kits_Words",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["word_uuid"],
            childColumns = ["kits_words_word_uuid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordKitEntity::class,
            parentColumns = ["word_kit_uuid"],
            childColumns = ["kits_words_kit_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordKitsWordsEntity(
    @PrimaryKey(autoGenerate = true)
    val kits_words_id: Int,
    val kits_words_word_uuid: String,
    val kits_words_kit_uuid: String
)