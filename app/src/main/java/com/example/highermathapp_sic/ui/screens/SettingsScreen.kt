package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.AppSettingsViewModel
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.remote.TaskFireStoreService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    appSettingsViewModel: AppSettingsViewModel
) {
    val settings = appSettingsViewModel.settings.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("NavigationScreen") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (settings.value?.mode == "offline") {
                Text("Внимание: при смене аккаунта данные не сохранятся.\n" +
                        "Все результаты и прогресс хранятся локально на устройстве и не привязаны к аккаунту.\n" +
                        "Если вы продолжите, текущие данные будут утеряны.",
                    style = MaterialTheme.typography.titleLarge)
            }

            Button(onClick = {
                if (settings.value?.mode == "offline") Firebase.auth.signOut()
                taskViewModel.clearAllTasks()
                navController.navigate("LoginScreen")
            }) {
                Text("Сменить аккаунт", style = MaterialTheme.typography.titleLarge)
            }

            Button(onClick = {
                taskViewModel.viewModelScope.launch {
                    taskViewModel.clearAllTasks()
                    if (settings.value?.mode == "offline") {
                        TaskFireStoreService.deleteAllTasksFromFireStore()
                        TaskFireStoreService.downloadTasksFromFireStoreOrInit(taskViewModel)
                    }
                }
                navController.navigate("MainScreen")
            }) {
                Text("Начать сначала", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}