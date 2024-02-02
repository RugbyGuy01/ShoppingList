package com.golfpvcc.shoppinglist.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.golfpvcc.shoppinglist.ui.coursedetail.CourseDetailScreen
import com.golfpvcc.shoppinglist.ui.courses.CoursesScreen
import com.golfpvcc.shoppinglist.ui.detail.DetailScreen
import com.golfpvcc.shoppinglist.ui.home.HomeScreen



enum class Routes {
    Home,
    Detail,
    Courses,
    CourseDetail,
    ScoreCard,
}

@Composable
fun ShoppingNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Courses.name,
    ) {
        /* -------------------------Shopping List Items -----------------------------------*/
        composable(route = Routes.Home.name) {
            Log.d("VIN", "ShoppingNavigation to detail screen")
            HomeScreen(onNavigate = { id ->
                navHostController.navigate(route = "${Routes.Detail.name}?id=$id")
            })
        }
        /* -------------------------Shopping List detail Item -----------------------------------*/
        composable(
            route = "${Routes.Detail.name}?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: -1
            DetailScreen(id = id) {
                navHostController.navigateUp()
            }
        }
        /* --------------------List of courses----------------------------------------*/

        composable(route = Routes.Courses.name) {
            Log.d("VIN", "ShoppingNavigation to CoursesScreen screen")
            CoursesScreen(onNavigate = { id ->
                navHostController.navigate(route = "${Routes.CourseDetail.name}?id=$id")
            })
        }
        /* --------------------Course Detail Screen----------------------------------------*/

        composable(
            route = "${Routes.CourseDetail.name}?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: -1
            Log.d("VIN", "ShoppingNavigation to CourseDetailScreen screen")
            CourseDetailScreen(id = id) {
                navHostController.navigateUp()
            }
        }
        /* --------------------Score Card Screen----------------------------------------*/

//        composable(
//            route = "${Routes.ScoreCard.name}?id={id}",
//            arguments = listOf(navArgument("id") { type = NavType.IntType })
//        ) {
//            val id = it.arguments?.getInt("id") ?: -1
//            Log.d("VIN", "ShoppingNavigation to ScoreCard screen")
//            ScoreCardScreen(id = id) {
//                navHostController.navigateUp()
//            }
//        }

    }
}
