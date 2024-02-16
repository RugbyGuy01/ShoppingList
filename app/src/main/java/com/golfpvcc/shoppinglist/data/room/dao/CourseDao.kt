package com.golfpvcc.shoppinglist.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.data.room.model.PlayerRecord
import com.golfpvcc.shoppinglist.data.room.model.ScoreCardRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Upsert
    suspend fun addUpdateCourseRecord(courseRecord: CourseRecord)

    @Delete
    suspend fun deleteCourseRecord(courseRecord: CourseRecord)

    @Query("SELECT * FROM CourseTable WHERE mId = :courseId ")
    fun getCourseRecord(courseId: Int?): Flow<CourseRecord>

    @Query("Select * FROM CourseTable ORDER BY mCoursename ASC")
    fun getAllCoursesRecordAsc(): Flow<List<CourseRecord>>
    @Query("Select * FROM CourseTable ORDER BY mCoursename DESC")
    fun getAllCoursesRecordDesc(): Flow<List<CourseRecord>>

    /* Score Card DAO interfaces */
    @Upsert
    suspend fun addUpdateScoreCardRecord(scoreCardRecord: ScoreCardRecord)

    @Query("Select * FROM ScoreCardRecord ")
    fun getScoreCardRecord(): Flow<ScoreCardRecord>

    // Player's record

    @Upsert
    suspend fun addUpdatePlayerRecord(playerRecord: PlayerRecord)

    @Query("SELECT * FROM PlayerRecord WHERE scoreCardRec_Fk = :scoreCardRec_Id ")
    fun getPlayerRecord(scoreCardRec_Id: Int): Flow<List<PlayerRecord>>


}