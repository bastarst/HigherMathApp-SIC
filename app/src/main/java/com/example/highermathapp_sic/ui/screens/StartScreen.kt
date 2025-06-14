package com.example.highermathapp_sic.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.AppSettingsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun StartScreen(
    navController: NavController,
    appSettingsViewModel: AppSettingsViewModel
) {
    val settings = appSettingsViewModel.settings.observeAsState()

    if (settings.value != null) {
        Log.d("ASVM", settings.value?.mode.toString())
        if (settings.value?.mode == "offline" || Firebase.auth.currentUser != null) {
            navController.navigate("MainScreen")
        } else {
            navController.navigate("LoginScreen")
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {  }
}