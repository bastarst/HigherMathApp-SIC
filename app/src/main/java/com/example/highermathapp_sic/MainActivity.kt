package com.example.highermathapp_sic

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.navigation.AppNav
import com.example.highermathapp_sic.ui.theme.HigherMathAppSICTheme

@Suppress("UNCHECKED_CAST")
class TaskModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HigherMathAppSICTheme {
                MathApp()
            }
        }
    }
}

@Composable
fun MathApp() {
    val owner = LocalViewModelStoreOwner.current

    owner?.let {
        val viewModel: TaskViewModel = viewModel(
            it,
            "TaskViewModel",
            TaskModelFactory(LocalContext.current.applicationContext as Application)
        )
        AppNav(viewModel)
    }
}