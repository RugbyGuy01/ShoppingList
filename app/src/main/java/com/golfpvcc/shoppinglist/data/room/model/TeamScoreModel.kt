package com.golfpvcc.shoppinglist.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CourseTable")
data class CourseRecord(
    val mCoursename: String,   // this is the database key for this course in the CourseListRecord class
    val mUsstate: String?,
    val mPar: IntArray,
    val mHandicap: IntArray,
    @PrimaryKey(autoGenerate = true)    // default is false
    val mId: Int = 0
)

@Entity(tableName = "ScoreCardRecord")
data class ScoreCardRecord(
    val mCourseName: String,    // current course name from the course list dtabase
    val mCurrentHole: Int = 0,      // the current hole being played in the game
    val mPar: IntArray,         // the current course Par
    val mHandicap: IntArray,       // current course handicap
    val mTeamHoleScore: IntArray,
    val mTeamTotalScore: IntArray,
    val mHoleUsedByPlayers: IntArray,
    @PrimaryKey
    val scoreCardRec_Id: Int,    // score card record ID
)

@Entity(tableName = "PlayerRecord")
data class PlayerRecord(
    val mName: String,   // this is the database key for this course in the CourseListRecord class
    val mHandicap: Int?,
    val mScore: IntArray,
    val scoreCardRec_Fk: Int,    // score card record ID
    @PrimaryKey(autoGenerate = false)    // default is false
    val mId: Int = 0
)