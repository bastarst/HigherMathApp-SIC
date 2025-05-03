package com.example.highermathapp_sic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.highermathapp_sic.ui.screens.calculus.*
import com.example.highermathapp_sic.ui.screens.differentialequations.*
import com.example.highermathapp_sic.ui.screens.linearalgebra.*
import com.example.highermathapp_sic.ui.screens.MainScreen
import com.example.highermathapp_sic.ui.screens.NavScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        composable("NavigationScreen") {
            NavScreen(navController)
        }
        composable("MainScreen") {
            MainScreen(navController)
        }
        composable("LAFirstScreen") {
            LAFirstScreen(navController)
        }
        composable("LASecondScreen") {
            LASecondScreen(navController)
        }
        composable("CFirstScreen") {
            CFirstScreen(navController)
        }
        composable("CSecondScreen") {
            CSecondScreen(navController)
        }
        composable("DEFirstScreen") {
            DEFirstScreen(navController)
        }
        composable("DESecondScreen") {
            DESecondScreen(navController)
        }
    }
}