package ua.vadymmy.it.words.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ua.vadymmy.it.words.data.local.entities.binding.LearningKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.PredefinedKitsWordsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningKitsEntity
import ua.vadymmy.it.words.data.local.entities.binding.UsersLearningWordsEntity
import ua.vadymmy.it.words.data.local.entities.other.LearningKitWithLearningWords
import ua.vadymmy.it.words.data.local.entities.other.PredefinedKitWithWords
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.LearningWordKitEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity

@Dao
interface WordsDao {

    //CREATE
    @Insert
    suspend fun insertWord(wordEntity: WordEntity)

    @Insert
    suspend fun insertLearningWordBinding(usersLearningWordsEntity: UsersLearningWordsEntity)

    @Insert
    suspend fun insertPredefinedWordKit(predefinedWordKitEntity: PredefinedWordKitEntity)

    @Insert
    suspend fun insertLearningWordKit(learningWordKitEntity: LearningWordKitEntity)

    @Insert
    suspend fun insertKitWordBinding(predefinedWordKitEntity: PredefinedKitsWordsEntity)

    @Insert
    suspend fun insertKitWordBinding(learningWordKitEntity: LearningKitsWordsEntity)

    @Insert
    suspend fun insertUserKitBinding(usersLearningKitsEntity: UsersLearningKitsEntity)
    //END CREATE

    //READ
    @Query("SELECT * FROM Predefined_Word_Kits")
    suspend fun getAllPredefinedKitsPreviews(): List<PredefinedWordKitEntity>

    @Query(
        "SELECT * FROM Predefined_Word_Kits " +
                "INNER JOIN PredefinedKits_Words ON pred_kits_words_kit_uuid = word_kit_uuid " +
                "INNER JOIN Words ON pred_kits_words_word_uuid = word_uuid " +
                "WHERE word_kit_uuid = :kitUUID"
    )
    suspend fun getPredefinedKitWords(kitUUID: String): List<PredefinedKitWithWords>

    @Query(
        "SELECT * FROM Learning_Word_Kits " +
                "INNER JOIN Users_Word_LearningKits ON users_kits_kit_uuid = word_kit_uuid " +
                "WHERE users_kits_user_id = :userId"
    )
    suspend fun getAllLearningPreviews(userId: String): List<LearningWordKitEntity>

    @Query(
        "SELECT * FROM Learning_Word_Kits " +
                "INNER JOIN LearningKits_Words ON learn_kits_words_kit_uuid = word_kit_uuid " +
                "INNER JOIN Words ON learn_kits_words_word_uuid = word_uuid " +
                "INNER JOIN Users_Learning_Words ON users_learning_words_word_uuid = word_uuid " +
                "WHERE users_learning_words_user_id = :userId AND word_kit_uuid = :kitUUID"
    )
    suspend fun getLearningKitWords(
        userId: String,
        kitUUID: String
    ): List<LearningKitWithLearningWords>

    @Query("SELECT EXISTS (SELECT * FROM Predefined_Word_Kits WHERE word_kit_uuid = :wordKitUUID)")
    suspend fun isKitExists(wordKitUUID: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM Words WHERE word_uuid = :wordUUID)")
    suspend fun isWordExists(wordUUID: String): Boolean

    @Query(
        "SELECT EXISTS " +
                "(SELECT * FROM Words " +
                "INNER JOIN Users_Learning_Words ON users_learning_words_word_uuid = word_uuid " +
                "WHERE users_learning_words_user_id = :userId AND word_uuid = :wordUUID)"
    )
    suspend fun isWordLearning(wordUUID: String, userId: String): Boolean
    //END READ

    //UPDATE
    @Update
    suspend fun updateLearningWordKit(learningWordKitEntity: LearningWordKitEntity)

    @Query("UPDATE Words SET word_complaints_amount = :complaintsAmount WHERE word_uuid = :wordUUID")
    suspend fun updateWordComplaints(wordUUID: String, complaintsAmount: Int)

    @Query(
        "UPDATE Users_Learning_Words SET users_learning_words_word_progress = :progress" +
                " WHERE users_learning_words_word_uuid = :wordUUID AND users_learning_words_user_id = :userId"
    )
    suspend fun updateLearningWordBinding(wordUUID: String, userId: String, progress: Int)
    //END UPDATE

    //DELETE
    @Delete
    suspend fun deleteLearningWordKit(learningWordKitEntity: LearningWordKitEntity)

    @Query("DELETE FROM LearningKits_Words WHERE learn_kits_words_word_uuid = :wordUUID AND learn_kits_words_kit_uuid = :kitUUID")
    suspend fun deleteLearningWordFromKit(kitUUID: String, wordUUID: String)
    //END DELETE
}