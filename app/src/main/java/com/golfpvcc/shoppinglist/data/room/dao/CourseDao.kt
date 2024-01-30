package com.golfpvcc.shoppinglist.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Upsert
    suspend fun addUpdateCourseRecord(courseRecord: CourseRecord)

    @Delete
    suspend fun deleteCourseRecord(courseRecord: CourseRecord)

    @Query("SELECT * FROM CourseTable WHERE mId = :courseId ")
    fun getCourseRecord(courseId: Int): Flow<CourseRecord>

    @Query("Select * FROM CourseTable ORDER BY mCoursename ASC")
    fun getAllCoursesRecordAsc(): Flow<List<CourseRecord>>
    @Query("Select * FROM CourseTable ORDER BY mCoursename DESC")
    fun getAllCoursesRecordDesc(): Flow<List<CourseRecord>>

}