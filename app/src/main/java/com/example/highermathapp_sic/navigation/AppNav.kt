package com.example.highermathapp_sic.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.ui.screens.calculus.*
import com.example.highermathapp_sic.ui.screens.linearalgebra.*
import com.example.highermathapp_sic.ui.screens.MainScreen
import com.example.highermathapp_sic.ui.screens.NavScreen

@Composable
fun AppNav(vm: TaskViewModel = viewModel()) {
    val navController = rememberNavController()

    val screensWithVm: List<Pair<String, @Composable (NavHostController, TaskViewModel) -> Unit>> = listOf(
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

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        for ((route, screen) in screensWithVm) {
            composable(route) {
                screen(navController, vm)
            }
        }
    }
}