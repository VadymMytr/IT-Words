package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.WordKitEntity

@Entity(
    tableName = "Users_Word_Kits",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["users_word_kits_user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordKitEntity::class,
            parentColumns = ["word_kit_uuid"],
            childColumns = ["users_word_kits_kit_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UsersWordKitsEntity(
    @PrimaryKey(autoGenerate = true)
    val users_word_kits_id: Int,
    val users_word_kits_user_id: String,
    val users_word_kits_kit_uuid: String
)