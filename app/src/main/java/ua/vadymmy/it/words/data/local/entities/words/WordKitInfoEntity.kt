package ua.vadymmy.it.words.data.local.entities.words

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.entities.word.common.WordImage

@Entity(tableName = "WordKitInfos")
data class WordKitInfoEntity(
    @PrimaryKey
    val word_kit_UUID: String,
    val word_kit_name: String,
    val word_kit_category_name: String,
    @Embedded(prefix = "word_kit_image_")
    val word_kit_image: WordImage
)