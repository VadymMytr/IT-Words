package ua.vadymmy.it.words.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey
    val user_id: String,
    val user_email: String,
    val user_name: String,
    val user_learn_progress: Int
)