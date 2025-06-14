package com.example.highermathapp_sic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.highermathapp_sic.model.AppSettingsViewModel
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.ui.screens.calculus.*
import com.example.highermathapp_sic.ui.screens.linearalgebra.*
import com.example.highermathapp_sic.ui.screens.MainScreen
import com.example.highermathapp_sic.ui.screens.NavScreen
import com.example.highermathapp_sic.ui.screens.SettingsScreen
import com.example.highermathapp_sic.ui.screens.StartScreen
import com.example.highermathapp_sic.ui.screens.authentication.LoginScreen
import com.example.highermathapp_sic.ui.screens.authentication.RegistrationScreen

@Composable
fun AppNav(
    taskViewModel: TaskViewModel,
    appSettingsViewModel: AppSettingsViewModel
) {
    val navController = rememberNavController()

    val screensWithTaskVm: List<Pair<String, @Composable (NavHostController, TaskViewModel) -> Unit>> = listOf(
        "MainScreen" to { nc, v -> MainScreen(nc, v) },
        "NavigationScreen" to { nc, v -> NavScreen(nc, v)},
        "MatrixAddSub" to { nc, v -> MatrixAddSub(nc, v) },
        "MatrixMul" to { nc, v -> MatrixMul(nc, v) },
        "MatrixDet" to { nc, v -> MatrixDet(nc, v) },
        "MatrixMinor" to { nc, v -> MatrixMinor(nc, v) },
        "MatrixInverse" to { nc, v -> MatrixInverse(nc, v) },
        "MatrixCramerRule" to { nc, v -> MatrixCramerRule(nc, v) },
        "SequenceLimits" to { nc, v -> SequenceLimits(nc, v) },
        "FunctionLimits" to { nc, v -> FunctionLimits(nc, v) },
        "FunctionAnalysis" to { nc, v -> FunctionAnalysis(nc, v) },
        "IndefiniteIntegrals" to { nc, v -> IndefiniteIntegrals(nc, v) },
        "DefiniteIntegrals" to { nc, v -> DefiniteIntegrals(nc, v) }
    )

    val screensWithoutVm: List<Pair<String, @Composable (NavHostController) -> Unit>> = listOf(
        //"StartScreen" to { nc -> StartScreen(nc) },
        "RegistrationScreen" to { nc -> RegistrationScreen(nc) }
    )

    NavHost(
        navController = navController,
        startDestination = "StartScreen"
    ) {
        composable("LoginScreen") {
            LoginScreen(navController, taskViewModel, appSettingsViewModel)
        }
        composable("SettingsScreen") {
            SettingsScreen(navController, taskViewModel, appSettingsViewModel)
        }
        composable("StartScreen") {
            StartScreen(navController, appSettingsViewModel)
        }

        for ((route, screen) in screensWithoutVm) {
            composable(route) {
                screen(navController)
            }
        }

        for ((route, screen) in screensWithTaskVm) {
            composable(route) {
                screen(navController, taskViewModel)
            }
        }
    }
}