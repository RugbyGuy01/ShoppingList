package com.golfpvcc.shoppinglist.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.golfpvcc.shoppinglist.ui.detail.DetailScreen
import com.golfpvcc.shoppinglist.ui.home.HomeScreen

enum class Routes {
    Home,
    Detail
}

@Composable
fun ShoppingNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.name,
    ) {
        composable(route = Routes.Home.name) {
            Log.d("VIN", "ShoppingNavigation to detail screen")
            HomeScreen(onNavigate = { id ->
                navHostController.navigate(route = "${Routes.Detail.name}?id=$id")
            })
        }
        composable(
            route = "${Routes.Detail.name}?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            Log.d("VIN", "ShoppingNavigation to Home screen")

            val id = it.arguments?.getInt("id") ?: -1
            DetailScreen(id = id) {
                navHostController.navigateUp()
            }
        }
    }
}
