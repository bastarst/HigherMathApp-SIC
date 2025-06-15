package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.svg.SvgDecoder
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
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val buttonHeightPx = remember { mutableStateOf(0) }

            if (settings.value?.mode == "offline") {
                Text("Внимание: при смене аккаунта данные не сохранятся.\n" +
                        "Все результаты и прогресс хранятся локально на устройстве и не привязаны к аккаунту.\n" +
                        "Если вы продолжите, текущие данные будут утеряны.",
                    style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(10.dp))
            }

            Button(onClick = {
                if (settings.value?.mode == "offline") Firebase.auth.signOut()
                taskViewModel.clearAllTasks()
                navController.navigate("LoginScreen")
            }) {
                Text("Сменить аккаунт", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    appSettingsViewModel.updateDarkTheme(
                        settings.value?.darkTheme != true
                    )
                },
                modifier = Modifier.onSizeChanged { size ->
                    buttonHeightPx.value = size.height
                }
            ) {
                Text("Сменить тему", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Это приложение является open source — его исходный код открыт и доступен для всех. Вы можете свободно изучать, использовать и улучшать его, внося свой вклад в развитие проекта.\n",
                style = MaterialTheme.typography.titleLarge
            )

            val context = LocalContext.current
            val density = LocalDensity.current
            val uriHandler = LocalUriHandler.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()
            Button(
                onClick = {
                    uriHandler.openUri("https://github.com/bastarst/HigherMathApp-SIC")
                },
                modifier = Modifier
                    .height(with(density) { buttonHeightPx.value.toDp() })
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                AsyncImage(
                    model = "file:///android_asset/github.svg",
                    contentDescription = "github",
                    imageLoader = imageLoader,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}