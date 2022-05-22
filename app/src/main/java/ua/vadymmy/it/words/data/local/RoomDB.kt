package ua.vadymmy.it.words.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.vadymmy.it.words.data.local.dao.UserDao
import ua.vadymmy.it.words.data.local.dao.WordsDao
import ua.vadymmy.it.words.data.local.entities.binding.LearningKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.PredefinedKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningKitsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningWordsEntity
import ua.vadymmy.it.words.data.local.entities.user.UserEntity
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.WordKitEntity

private const val CURRENT_DB_VERSION = 2
const val ROOM_DATABASE_NAME = "room_local_db"

@Database(
    entities = [
        WordEntity::class,
        WordKitEntity::class,
        PredefinedWordKitEntity::class,
        LearningWordKitEntity::class,
        LearningKitsWordsEntity::class,
        PredefinedKitsWordsEntity::class,
        UserEntity::class,
        UsersLearningKitsEntity::class,
        UsersLearningWordsEntity::class
    ],
    version = CURRENT_DB_VERSION
)
abstract class RoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun wordsDao(): WordsDao
}