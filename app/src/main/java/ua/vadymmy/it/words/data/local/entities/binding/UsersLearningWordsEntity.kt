package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.WordEntity

@Entity(
    tableName = "Users_Learning_Words",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["users_words_user_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["word_uuid"],
            childColumns = ["users_words_word_uuid"],
            onDelete = CASCADE
        )
    ]
)
data class UsersLearningWordsEntity(
    @PrimaryKey(autoGenerate = true)
    val users_words_id: Int,
    val users_words_user_id: String,
    val users_words_word_uuid: String,
    val users_words_word_progress: Int
)