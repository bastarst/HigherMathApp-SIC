package com.example.highermathapp_sic.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.ui.components.MathAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    val dbList = vm.taskList.observeAsState(listOf())

    Scaffold(
        topBar = { MathAppTopBar("Главная", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Main Screen",
            )
            Spacer(modifier = Modifier.height(16.dp))
            dbList.value.forEach { task ->
                Row {
                    Text(
                        text = "${task.id} | ${task.taskGroup} | ${task.taskType} | ${task.taskContent}"
                    )
                    IconButton(onClick = {vm.deleteTask(task.id)}) {
                        Icon(Icons.Default.Delete, contentDescription = "Del")
                    }
                }
            }
        }
    }
}