package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.MatrixInput
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixAddSub(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    BaseScreenLayout(
        navController = navController,
        title = "Матрицы. Сложение и вычитание",
        onPrevious = "MainScreen",
        onNext = "MatrixMul"
    ) {
        TheoreticalPart(TaskGroup.LINEAR_ALGEBRA, "matrix_add_sub.txt")
        TaskSection("Сложите матрицы:") {
            TaskMatrixAdd(vm)
            TaskMatrixSub(vm)
        }
    }
}

@Composable
fun TaskMatrixAdd(vm: TaskViewModel) {
    val matrixList = vm.taskList.observeAsState(listOf())

    Row {
        if(matrixList.value.isEmpty()) {
            Text("Загрузка", style = MaterialTheme.typography.titleLarge)
        } else {
            val lastMatrix = matrixList.value.lastOrNull {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.ADDITION
            }!!
            val isAnswerCorrect = lastMatrix.isAnswerCorrect
            val matrixEntity = lastMatrix
            val (matrixA, matrixB) = TaskContentConverter.decodePairMatrix(matrixEntity.taskContent!!)
            val correctAnswer = matrixA + matrixB
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MatrixView(matrixA)

                    Text("+", style = MaterialTheme.typography.titleLarge)

                    MatrixView(matrixB)

                    Text("=", style = MaterialTheme.typography.titleLarge)

                    MatrixInput(
                        matrixAnswer = correctAnswer,
                        isAnswerCorrect = isAnswerCorrect,
                        viewModel = vm,
                        taskEntity = lastMatrix
                    )
                }

                IsAnswerCorrect(isAnswerCorrect)
            }
        }
    }
}

@Composable
fun TaskMatrixSub(vm: TaskViewModel) {
    val matrixList = vm.taskList.observeAsState(listOf())

    Row {
        if(matrixList.value.isEmpty()) {
            Text("Загрузка", style = MaterialTheme.typography.titleLarge)
        } else {
            val lastMatrix = matrixList.value.lastOrNull {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.SUBTRACTION
            }!!
            val isAnswerCorrect = lastMatrix.isAnswerCorrect
            val matrixEntity = lastMatrix
            val (matrixA, matrixB) = TaskContentConverter.decodePairMatrix(matrixEntity.taskContent!!)
            val correctAnswer = matrixA - matrixB

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MatrixView(matrixA)

                    Text("-", style = MaterialTheme.typography.titleLarge)

                    MatrixView(matrixB)

                    Text("=", style = MaterialTheme.typography.titleLarge)

                    MatrixInput(
                        matrixAnswer = correctAnswer,
                        isAnswerCorrect = isAnswerCorrect,
                        viewModel = vm,
                        taskEntity = lastMatrix
                    )
                }

                IsAnswerCorrect(isAnswerCorrect)
            }
        }
    }
}