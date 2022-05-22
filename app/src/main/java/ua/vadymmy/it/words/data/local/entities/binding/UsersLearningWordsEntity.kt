package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.domain.models.word.common.Word

@Entity(
    tableName = "Users_Learning_Words",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["users_learning_words_user_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["word_uuid"],
            childColumns = ["users_learning_words_word_uuid"],
            onDelete = CASCADE
        )
    ]
)
data class UsersLearningWordsEntity(
    @PrimaryKey(autoGenerate = true)
    var users_learning_words_id: Int = 0,
    var users_learning_words_user_id: String,
    var users_learning_words_word_uuid: String,
    var users_learning_words_word_progress: Int
) {

    constructor(userUid: String, word: Word) : this(
        users_learning_words_user_id = userUid,
        users_learning_words_word_uuid = word.uuid,
        users_learning_words_word_progress = word.progress
    )
}