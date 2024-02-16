package com.golfpvcc.shoppinglist.ui.repository

import com.golfpvcc.shoppinglist.data.room.dao.CourseDao
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.data.room.model.PlayerRecord
import com.golfpvcc.shoppinglist.data.room.model.ScoreCardRecord

class CourseRepository(
    private val courseDao: CourseDao,
) {
    val getAllCourses = courseDao.getAllCoursesRecordAsc()

    suspend fun addUpdateCourseRecord(courseRecord: CourseRecord) {
        courseDao.addUpdateCourseRecord(courseRecord)
    }

    suspend fun deleteCourseRecord(courseRecord: CourseRecord) {
        courseDao.deleteCourseRecord(courseRecord)
    }

    fun getCourseRecordById(iD: Int?) =
        courseDao.getCourseRecord(iD)

    /* Score Card DAO interfaces */
    suspend fun addUpdateScoreCardRecord(scoreCardRecord: ScoreCardRecord) {
        courseDao.addUpdateScoreCardRecord(scoreCardRecord)
    }

    fun getScoreCardRecord() = courseDao.getScoreCardRecord()

    // Player setup repository
    suspend fun addUpdatePlayerRecord(playerRecord: PlayerRecord) {
        courseDao.addUpdatePlayerRecord(playerRecord)
    }

    fun getPlayerRecord(scoreCardRec_Id: Int) =
        courseDao.getPlayerRecord(scoreCardRec_Id)

}