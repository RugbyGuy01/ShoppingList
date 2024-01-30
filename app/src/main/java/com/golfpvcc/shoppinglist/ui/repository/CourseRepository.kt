package com.golfpvcc.shoppinglist.ui.repository

import com.golfpvcc.shoppinglist.data.room.dao.CourseDao
import com.golfpvcc.shoppinglist.data.room.dao.ListDao
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.data.room.model.Item

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

    fun getCourseRecordById(iD:Int) =
        courseDao.getCourseRecord(iD)

}