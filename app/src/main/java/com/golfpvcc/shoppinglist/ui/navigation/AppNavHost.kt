package com.golfpvcc.shoppinglist.ui.navigation

import ScoreCardScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.golfpvcc.shoppinglist.ui.coursedetail.CourseDetailScreen
import com.golfpvcc.shoppinglist.ui.courses.CoursesScreen
import com.golfpvcc.shoppinglist.ui.playersetup.PlayerSetupScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = "Configuration"
    ) {

        navigation(
            startDestination = Screen.Courses.route,
            route = "Configuration"
        ) {
            composable(route = Screen.Courses.route) {
//                val viewModel = it.sharedViewModel<ScreensViewModel>(navHostController)
                CoursesScreen(navHostController)
            }

            composable(route = Screen.DetailCourse.route,
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )) { id ->
                Log.d("VIN", "SetupNavGraph CourseDetail $id")
                CourseDetailScreen(
                    navHostController, id.arguments?.getInt("id")
                )
            }

            composable(route = Screen.PlayerSetup.route,
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )) { id ->
                PlayerSetupScreen(
                    navHostController, id.arguments?.getInt("id")
                )
            }
        } // end of nested navation

        navigation(
            startDestination = "ScoreCard",
            route = "GameOn"
        ) {
            composable(route =Screen.ScoreCard.route,
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )) { id ->
                ScoreCardScreen(
                    navHostController, id.arguments?.getInt("id")
                )
            }

        }
    }
}
// get view model from a shared nest navigation graph
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navHostController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navHostController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}