package ua.vadymmy.it.words.data.local.entities.binding

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.domain.models.word.kit.WordKit

@Entity(
    tableName = "Users_LearningKits",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["users_kits_user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LearningWordKitEntity::class,
            parentColumns = ["word_kit_uuid"],
            childColumns = ["users_kits_kit_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UsersLearningKitsEntity(
    @PrimaryKey(autoGenerate = true)
    var users_kits_id: Int = 0,
    var users_kits_user_id: String,
    var users_kits_kit_uuid: String
) {

    constructor(userUid: String, kit: WordKit) : this(
        users_kits_user_id = userUid,
        users_kits_kit_uuid = kit.uuid
    )
}