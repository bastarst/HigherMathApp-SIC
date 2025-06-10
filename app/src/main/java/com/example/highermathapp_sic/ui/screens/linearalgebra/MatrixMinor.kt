package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.SimpleQuestionTask
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@Composable
fun MatrixMinor(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Матрицы. Минор",
        onPrevious = "MatrixDet",
        onNext = "MatrixInverse"
    ) {
        TheoreticalPart(TaskGroup.LINEAR_ALGEBRA, "matrix_minor.txt")
        TaskMatrixMinor(vm)
    }
}

@Composable
fun TaskMatrixMinor(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.MINOR
        }!!
        val (minor, matrix) = TaskContentConverter.decodeMinor(taskEntity.taskContent!!)
        val correctAnswer = matrix.minor(minor.first, minor.second).toString()

        Row(modifier = Modifier.padding(8.dp)) {
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
}