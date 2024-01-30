package com.golfpvcc.shoppinglist.ui.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.golfpvcc.shoppinglist.Graph
import com.golfpvcc.shoppinglist.data.room.model.CourseRecord
import com.golfpvcc.shoppinglist.data.room.model.Item
import com.golfpvcc.shoppinglist.data.room.model.ShoppingList
import com.golfpvcc.shoppinglist.data.room.model.Store
import com.golfpvcc.shoppinglist.ui.Category
import com.golfpvcc.shoppinglist.ui.HolePar
import com.golfpvcc.shoppinglist.ui.Utils
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import com.golfpvcc.shoppinglist.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class CourseDetailViewModel
constructor(
    private val courseId: Int,
    private val repository: CourseRepository = Graph.courseRepository,
) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class CourseDetailViewModelFactor(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CourseDetailViewModel(courseId = id) as T
        }
    }

    var state by mutableStateOf(CourseDetailState())
        private set

    init {
        if (courseId != -1) {
            viewModelScope.launch {
                repository.getCourseRecordById(courseId)
                    .collectLatest {
                        state = state.copy(
                            mCoursename = it.mCoursename,
                            mUsstate = it.mUsstate,
                            mPar = it.mPar,
                            mHandicap = it.mHandicap
                        )
                    }
            }
        }
    }

    init {
        state = if (courseId != -1) {
            state.copy(isUpdatingCourse = true)
        } else {
            state.copy(isUpdatingCourse = false)
        }
    }

//    fun onCourseNameChange(newValue: String) {
//        state = state.copy(mCoursename = newValue)
//    }

//    fun onDisplayFront9Change(newValue: Boolean) {
//        state = state.copy(mDisplayFrontNine = !newValue)
//    }

    fun onDropDownDismissed(newValue: Boolean) {
        state = state.copy(isDropDownDismissed = !newValue)
    }
    fun onPopupSelectHolePar(newValue: Boolean) {
        state = state.copy(isPopupSelectHolePar = newValue)
    }
    fun onParChange(holeIdx: Int, newValue: Int) {
        Log.d("VIN", "onParChange Inx$holeIdx - Par $newValue")
        state.mPar[holeIdx] = newValue
    }

    fun onHandicapChange(holeIdx: Int, newValue: Int) {
        Log.d("VIN", "onHandicapChange Inx$holeIdx - Par $newValue")
        state.mHandicap[holeIdx] = newValue
    }

    fun onUsStateChange(newValue: String) {
        state = state.copy(mUsstate = newValue)
    }

    fun saveCourseRecord() {
        viewModelScope.launch {
            repository.addUpdateCourseRecord(
                CourseRecord(
                    mCoursename = state.mCoursename,   // this is the database key for this course in the CourseListRecord class
                    mUsstate = state.mUsstate,
                    mPar = state.mPar,
                    mHandicap = state.mHandicap
                )
            )
        }
    }
}

data class CourseDetailState(
    val mCoursename: String = "",   // this is the database key for this course in the CourseListRecord class
    val mUsstate: String? = "",
    val mDisplayFrontNine: Boolean = true,
    val mPar: IntArray = IntArray(18) { 4 },
    val mHandicap: IntArray = IntArray(18),
    val isUpdatingCourse: Boolean = false,
    val isPopupSelectHolePar: Boolean = false,
    val isDropDownDismissed: Boolean = false,
    val category: Category = Category(),
    val holePar: HolePar = HolePar()
)