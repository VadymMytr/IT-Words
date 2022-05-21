package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users_Words")
data class UsersWordsEntity(
    @PrimaryKey(autoGenerate = true)
    val users_words_id: Int,
    val users_words_user_id: String,
    val users_words_word_UUID: String,
    val users_words_word_progress: Int
)