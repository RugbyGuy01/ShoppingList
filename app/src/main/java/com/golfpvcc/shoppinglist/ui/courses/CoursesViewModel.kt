package com.golfpvcc.shoppinglist.ui.courses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golfpvcc.shoppinglist.Graph
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val repository: CourseRepository = Graph.courseRepository
) : ViewModel() {
    var state by mutableStateOf(CoursesState())
        private set

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModelScope.launch {
            repository.getAllCourses.collectLatest {
                state = state.copy(
                    courseRecords = it
                )
            }
        }
    }

    fun deleteCourse(courseRecord: CourseRecord) {
        viewModelScope.launch {
            repository.deleteCourseRecord(courseRecord)
        }
    }

}

data class CoursesState(
    val courseRecords: List<CourseRecord> = emptyList(),
)