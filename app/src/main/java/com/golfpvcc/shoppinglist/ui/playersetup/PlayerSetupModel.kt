package com.golfpvcc.shoppinglist.ui.playersetup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.golfpvcc.shoppinglist.Graph
import com.golfpvcc.shoppinglist.data.room.model.ScoreCardRecord
import com.golfpvcc.shoppinglist.ui.repository.CourseRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayerSetupViewModel(
    private val courseId: Int?,
    private val repository: CourseRepository = Graph.courseRepository,
) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class PlayerSetupViewModelFactor(private val id: Int?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlayerSetupViewModel(courseId = id) as T
        }
    }

    var state by mutableStateOf(ScoreCardState())
        private set

    init {
        if (courseId != -1) {
            viewModelScope.launch {
                repository.getCourseRecordById(courseId)
                    .collectLatest {
                        state = state.copy(
                            mCourseName = it.mCoursename,
                            mPar = it.mPar,
                            mHandicap = it.mHandicap,
                        )
                    }
            }
            saveScoreCardRecord()
        } else {
            Log.d("VIN", "No record Id of $courseId")
        }
    }    // end of init
    fun saveScoreCardRecord() {
        val scoreCardRecord: ScoreCardRecord = ScoreCardRecord(
            state.mCourseName,
            state.mCurrentHole,
            state.mPar,
            state.mHandicap,
            state.mTeamHoleScore,
            state.mTeamTotalScore,
            state.mHoleUsedByPlayers,
            state.scoreCardRec_Id
        )
        viewModelScope.launch {
            repository.addUpdateScoreCardRecord(scoreCardRecord)
        }
    }
}

data class PlayerSetup(
    val mId: Int = 0,
    val mName: String = "",   // this is the database key for this course in the CourseListRecord class
    val mHandicap: Int? = 0,
    val scoreCardRec_Fk: Int = 0,    // score card record ID
)
data class ScoreCardState(
    val mCourseName: String = "",    // current course name from the course list dtabase
    val mCurrentHole: Int = 0,      // the current hole being played in the game
    val mPar: IntArray = IntArray(18),        // the current course Par
    val mHandicap: IntArray = IntArray(18),       // current course handicap
    val mTeamHoleScore: IntArray = IntArray(18),
    val mTeamTotalScore: IntArray = IntArray(18),
    val mHoleUsedByPlayers: IntArray = IntArray(18),
    var mPlayerSetupState: Array<PlayerSetup> = Array<PlayerSetup>(5) { PlayerSetup() },
    @PrimaryKey
    val scoreCardRec_Id: Int = 10,    // score card record ID
)
