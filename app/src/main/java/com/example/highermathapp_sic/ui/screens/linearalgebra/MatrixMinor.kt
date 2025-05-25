package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.SimpleQuestionTask

@Composable
fun MatrixMinor(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Матрицы. Минор"
    ) {
        Text("MINOR")
        TaskMatrixMinor(vm)
    }
}

@Composable
fun TaskMatrixMinor(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull() {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.MINOR
        }!!
        val (minor, matrix) = TaskContentConverter.decodeMinor(taskEntity.taskContent!!)
        val correctAnswer = matrix.minor(minor.first, minor.second).toString()

        SimpleQuestionTask(
            correctAnswer = correctAnswer,
            vm = vm,
            taskGroup = taskEntity.taskGroup!!,
            taskType = taskEntity.taskType!!
        ) {
            Text(
                text = "Найдите M(${minor.first + 1}, ${minor.second + 1}) матрицы матрицы:",
                style = MaterialTheme.typography.titleLarge
            )
            MatrixView(matrix)
        }
    }
}