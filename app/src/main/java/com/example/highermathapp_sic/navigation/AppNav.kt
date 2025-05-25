package com.example.highermathapp_sic.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.ui.screens.calculus.*
import com.example.highermathapp_sic.ui.screens.differentialequations.*
import com.example.highermathapp_sic.ui.screens.linearalgebra.*
import com.example.highermathapp_sic.ui.screens.MainScreen
import com.example.highermathapp_sic.ui.screens.NavScreen

@Composable
fun AppNav(vm: TaskViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        composable("NavigationScreen") {
            NavScreen(navController)
        }
        composable("MainScreen") {
            MainScreen(navController, vm)
        }
        composable("MatrixAddSub") {
            MatrixAddSub(navController, vm)
        }
        composable("MatrixMul") {
            MatrixMul(navController, vm)
        }
        composable("MatrixDet") {
            MatrixDet(navController, vm)
        }
        composable("MatrixMinor") {
            MatrixMinor(navController, vm)
        }
        composable("MatrixInverse") {
            MatrixInverse(navController, vm)
        }
        composable("MatrixCramerRule") {
            MatrixCramerRule(navController, vm)
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