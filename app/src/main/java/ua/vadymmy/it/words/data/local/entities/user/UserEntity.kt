package ua.vadymmy.it.words.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.domain.models.user.User

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey
    var user_id: String,
    var user_email: String,
    var user_name: String,
    var user_photo_url: String,
    var user_learn_progress: Int
) {
    constructor(user: User) : this(
        user.uid,
        user.email,
        user.name,
        user.photoUrl.toString(),
        user.learnProgress
    )
}