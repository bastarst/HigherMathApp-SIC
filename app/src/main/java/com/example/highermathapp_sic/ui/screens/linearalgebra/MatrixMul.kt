package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.MathAppTopBar
import com.example.highermathapp_sic.ui.components.MatrixInput
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixMul(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    BaseScreenLayout(
        navController = navController,
        title = "Матрицы. Умножение",
        onPrevious = "MatrixAddSub",
        onNext = "MatrixDet"
    ) {
        TheoreticalPart(TaskGroup.LINEAR_ALGEBRA, "matrix_mul.txt")
        TaskSection("Решите:") {
            TaskMulMatrixByNum(vm)
            TaskMulMatrixByMatrix1(vm)
            TaskMulMatrixByMatrix2(vm)
        }
    }
}

@Composable
fun TaskMulMatrixByNum(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    Row {
        if(taskList.value.isEmpty()) {
            Text("Загрузка", style = MaterialTheme.typography.titleLarge)
        } else {
            val taskEntity = taskList.value.lastOrNull() {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.MULTIPLICATION_BY_NUM
            }!!
            val isAnswerCorrect = taskEntity.isAnswerCorrect
            val (num ,matrix) = TaskContentConverter.decodeNumAndMatrix(taskEntity!!.taskContent!!)
            val correctAnswer = matrix * num

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(num.toString(), style = MaterialTheme.typography.titleLarge)

                    Icon(Icons.Default.Clear, contentDescription = "Mul")

                    MatrixView(matrix)

                    Text("=", style = MaterialTheme.typography.titleLarge)

                    MatrixInput(
                        matrixAnswer = correctAnswer,
                        isAnswerCorrect = isAnswerCorrect,
                        viewModel = vm,
                        taskEntity = taskEntity
                    )
                }

                IsAnswerCorrect(isAnswerCorrect)
            }
        }
    }
}

@Composable
fun TaskMulMatrixByMatrix1(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    Row {
        if(taskList.value.isEmpty()) {
            Text("Загрузка", style = MaterialTheme.typography.titleLarge)
        } else {
            val lastMatrix = taskList.value.lastOrNull {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.MULTIPLICATION_BY_MATRIX_1
            }!!
            val isAnswerCorrect = lastMatrix.isAnswerCorrect
            val matrixEntity = lastMatrix
            val (matrixA, matrixB) = TaskContentConverter.decodePairMatrix(matrixEntity.taskContent!!)
            val correctAnswer = matrixA * matrixB

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MatrixView(matrixA)

                    Icon(Icons.Default.Clear, contentDescription = "Mul")

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
fun TaskMulMatrixByMatrix2(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    Row {
        if(taskList.value.isEmpty()) {
            Text("Загрузка")
        } else {
            val lastMatrix = taskList.value.lastOrNull {
                it.taskGroup == TaskGroup.LINEAR_ALGEBRA
                it.taskType == TaskType.MULTIPLICATION_BY_MATRIX_2
            }!!
            val isAnswerCorrect = lastMatrix.isAnswerCorrect
            val matrixEntity = lastMatrix
            val (matrixA, matrixB) = TaskContentConverter.decodePairMatrix(matrixEntity.taskContent!!)
            val correctAnswer = matrixA * matrixB

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MatrixView(matrixA)

                    Icon(Icons.Default.Clear, contentDescription = "Mul")

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