package com.example.highermathapp_sic

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.highermathapp_sic.model.AppSettingsViewModel
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.navigation.AppNav
import com.example.highermathapp_sic.ui.theme.HigherMathAppSICTheme

@Suppress("UNCHECKED_CAST")
class TaskModelFactory(val application: Application, val mode: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(application, mode) as T
    }
}

@Suppress("UNCHECKED_CAST")
class AppSettingsViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppSettingsViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {
    private lateinit var appSettingsViewModel: AppSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsFactory = AppSettingsViewModelFactory(application)
        appSettingsViewModel = ViewModelProvider(this, settingsFactory)[AppSettingsViewModel::class.java]

        setContent {
            HigherMathAppSICTheme {
                val settings = appSettingsViewModel.settings.observeAsState()

                val isOnline = settings.value?.mode == "online"

                val taskViewModel: TaskViewModel = viewModel(
                    factory = TaskModelFactory(application, isOnline)
                )

                AppNav(taskViewModel, appSettingsViewModel)
            }
        }
    }
}