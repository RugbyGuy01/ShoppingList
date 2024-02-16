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
import com.golfpvcc.shoppinglist.ui.coursedetail.currentHandicapConfiguration
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val courseId: Int?,
    private val repository: CourseRepository = Graph.courseRepository,
) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class CourseDetailViewModelFactor(private val id: Int?) : ViewModelProvider.Factory {
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
                        setHandicapAvailable()  // wait for the record to be read from the database
                    }
            }
        } else {
            setHandicapAvailable()  // set up blank course
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

    fun getFlipHdcps(): Boolean {
        return state.mFlipHdcps
    }

    fun onFlipHdcpsChange(newValue: Boolean) {
        state = state.copy(mFlipHdcps = !newValue)
    }

    // Configure course Par
    fun onPopupSelectHolePar(holeIdx: Int) {
        state = state.copy(isPopupSelectHolePar = holeIdx)
    }

    fun getPopupSelectHolePar(): Int {
        return (state.isPopupSelectHolePar)
    }

    fun getHolePar(holeIdx: Int): Int {
        val newValue = state.mPar[holeIdx]
        Log.d("VIN", "getHolePar Inx$holeIdx - Par $newValue")
        return newValue
    }

    fun onParChange(holeIdx: Int, newValue: Int) {
        Log.d("VIN", "onParChange Inx$holeIdx - Par $newValue")
        state.mPar[holeIdx] = newValue
    }
// End Configure course Par

    // Configure course Handicap
    fun onPopupSelectHoleHdcp(holeIdx: Int) {
        state = state.copy(isPopupSelectHoleHdcp = holeIdx)
    }

    fun getPopupSelectHoleHandicap(): Int {
        return (state.isPopupSelectHoleHdcp)
    }

    fun getHoleHandicap(holeIdx: Int): Int {
        val newValue = state.mHandicap[holeIdx]
        Log.d("VIN", "getHoleHandicap Inx$holeIdx - Hdcp $newValue")
        return newValue
    }

    fun onHandicapChange(holeIdx: Int, newHdcp: Int) {
        Log.d("VIN", "onHandicapChange Card Hole-$holeIdx - Hdcp $newHdcp")
        state.mHandicap[holeIdx] = newHdcp
    }

    // End Configure course Handicap
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

    fun setHandicapAvailable() {
        currentHandicapConfiguration(state.mHandicap, state.availableHandicap)
    }
}

data class CourseDetailState(
    val mId: Int = 0,
    val mCoursename: String = "",   // this is the database key for this course in the CourseListRecord class
    val mUsstate: String? = "",
    val mFlipHdcps: Boolean = true,
    val mHoleNumber: IntArray = IntArray(18) { i -> i + 1 },
    val mPar: IntArray = IntArray(18) { 4 },
    val mHandicap: IntArray = IntArray(18) { 0 },
    val isUpdatingCourse: Boolean = false,
    val isPopupSelectHolePar: Int = -1,
    val isPopupSelectHoleHdcp: Int = -1,
    val isDropDownDismissed: Boolean = false,
    val parList: HoleParList = HoleParList(),
    var availableHandicap: Array<HoleHandicap> = Array(18) { HoleHandicap(0, false) }
)


data class HoleHandicap(
    var holeHandicap: Int = 0,
    var available: Boolean = false,      // has the hole handicap been used?
)

