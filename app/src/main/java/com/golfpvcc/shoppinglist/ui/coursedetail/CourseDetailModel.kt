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
import com.golfpvcc.shoppinglist.ui.HoleParList
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
                            mHandicap = it.mHandicap,
                            mId = it.mId
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

    fun onCourseNameChange(newValue: String) {
        state = state.copy(mCoursename = newValue)
    }

    fun onDisplayFront9Change(newValue: Boolean) {
        state = state.copy(mDisplayFrontNine = !newValue)
    }

    fun onDropDownDismissed(newValue: Boolean) {
        state = state.copy(isDropDownDismissed = newValue)
    }

    fun onSelectHole(holeIdx: Int, newValue: Int) {
        state.mHoleNumber[holeIdx] = newValue
    }

    fun onPopupSelectHolePar(newValue: Int) {
        state = state.copy(isPopupSelectHolePar = newValue)
    }
    fun getPopupSelectHolePar():Int {
        return (state.isPopupSelectHolePar)
    }

    fun onParChange(holeIdx: Int, newValue: Int) {
        Log.d("VIN", "onParChange Inx$holeIdx - Par $newValue")
        state.mPar[holeIdx] = newValue
    }

    fun getHolePar(holeIdx: Int): Int {
        val newValue = state.mPar[holeIdx]
        Log.d("VIN", "getHolePar Inx$holeIdx - Par $newValue")
        return newValue
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
                    mHandicap = state.mHandicap,
                    mId = state.mId,
                )
            )
        }
    }

    fun HoleDetailInfo(mHeader: String, mHole: IntArray, mEnd: String): HoleDetailInfo {
        return HoleDetailInfo(mHeader, mHole, mEnd)
    }
}

data class CourseDetailState(
    val mId: Int = 0,
    val mCoursename: String = "",   // this is the database key for this course in the CourseListRecord class
    val mUsstate: String? = "",
    val mDisplayFrontNine: Boolean = true,
    val mHoleNumber: IntArray = IntArray(18) { i -> i + 1 },
    val mPar: IntArray = IntArray(18) { 4 },
    val mHandicap: IntArray = IntArray(18) { 0 },
    val isUpdatingCourse: Boolean = false,
    val isPopupSelectHolePar: Int = -1,
    val isDropDownDismissed: Boolean = false,
    val parList: HoleParList = HoleParList(),
    var holeDetailInfo: HoleDetailInfo = HoleDetailInfo("header", mPar, "Total")
)


data class HoleDetailInfo(
    val mHeader: String = "Header",
    val mHole: IntArray = IntArray(18) { 0 },
    val mEnd: String = "Total",
)