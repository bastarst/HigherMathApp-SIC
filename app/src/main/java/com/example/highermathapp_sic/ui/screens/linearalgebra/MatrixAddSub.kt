package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.MathAppTopBar
import com.example.highermathapp_sic.ui.components.MatrixInput
import com.example.highermathapp_sic.ui.components.MatrixView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LAFirstScreen(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    Scaffold(
        topBar = { MathAppTopBar("Main", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "LA TEST 1",
            )
            TaskSection(vm)
        }
    }
}

@Composable
fun TaskSection(vm: TaskViewModel) {
    Task1(vm)
}

@Composable
fun Task1(vm: TaskViewModel) {
    val matrixList = vm.taskList.observeAsState(listOf())

    Row() {
        if(matrixList.value.isEmpty()) {
            Text("Загрузка")
        } else {
            val lastMatrix = matrixList.value.lastOrNull {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.ADDITION
            }
            val matrixEntity = lastMatrix!!
            val (matrixA, matrixB) = TaskContentConverter.decode(matrixEntity.taskContent!!)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MatrixView(matrixA)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(16.dp))
                MatrixView(matrixB)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Menu, contentDescription = "Eq")
                Spacer(modifier = Modifier.width(16.dp))
                if (matrixEntity.isAnswerCorrect == true) {
                    MatrixView(matrixA + matrixB)
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = {
                        vm.updateTaskWithRandom2X2Matrices(matrixEntity.id)
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Del")
                    }
                } else {
                    MatrixInput(matrixA + matrixB) { result ->
                        vm.updateAnswerCorrect(matrixEntity.id, result)
                    }
                }
            }
            matrixEntity.isAnswerCorrect?.let {
                Text(
                    text = if (it) "✅ Ответ верный" else "❌ Ответ неверный"
                )
            }
        }
    }
}