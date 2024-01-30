package com.golfpvcc.shoppinglist.ui.scorecard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.golfpvcc.shoppinglist.Graph
import com.golfpvcc.shoppinglist.ui.courses.CoursesState
import com.golfpvcc.shoppinglist.ui.detail.CourseDetailState
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository

class ScoreCardViewModel
constructor(
    private val courseId: Int,
    private val repository: CourseRepository = Graph.courseRepository,
) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class ScoreCardViewModelFactor(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScoreCardViewModel(courseId = id) as T
        }
    }

    var state by mutableStateOf(ScoreCardState())
        private set
    fun onCourseNameChange(newValue: String) {
        state = state.copy(mCoursename = newValue)
    }
    fun onDisplayFront9Change(newValue: Boolean) {
        state = state.copy(mDisplayFrontNine = !newValue)
    }

}

data class ScoreCardState(
    val mCoursename: String = "Pinehurst 2",   // this is the database key for this course in the CourseListRecord class
    val mPar: IntArray = IntArray(18) { 4 },
    val mDisplayFrontNine: Boolean = true,
)