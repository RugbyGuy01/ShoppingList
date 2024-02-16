package com.golfpvcc.shoppinglist.ui.navigation


sealed class Screen(val route: String) {
    object Courses : Screen(route = "Courses")
    object DetailCourse : Screen(route = "CourseDetail?id={id}") {
        fun passId(id: Int = -1): String {
            return "CourseDetail?id=$id"
        }
    }
    object PlayerSetup : Screen(route = "PlayerSetup?id={id}"){
        fun passId(id: Int = -1): String {
            return "PlayerSetup?id=$id"
        }
    }
    object ScoreCard : Screen(route = "ScoreCard?id={id}"){
        fun passId(id: Int = -1): String {
            return "ScoreCard?id=$id"
        }
    }
}