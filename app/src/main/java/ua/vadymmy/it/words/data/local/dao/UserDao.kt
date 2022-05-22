package ua.vadymmy.it.words.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ua.vadymmy.it.words.data.local.entities.user.UserEntity

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM Users WHERE user_id = :userId")
    fun getUserDetails(userId: String): UserEntity

    @Query("SELECT EXISTS (SELECT * FROM Users WHERE user_id = :userId)")
    fun isUserExists(userId: String): Boolean

    @Update
    fun updateUser(user: UserEntity)
}