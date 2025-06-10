package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.NumberInputField
import com.example.highermathapp_sic.ui.components.SimpleQuestionTask
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@Composable
fun MatrixDet(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Матрицы. Определитель",
        onPrevious = "MatrixMul",
        onNext = "MatrixMinor"
    ) {
        TheoreticalPart(TaskGroup.LINEAR_ALGEBRA, "matrix_det.txt")
        TaskSection("Найдите определитель матриц:") {
            TaskMatrixDet2X2(vm)
            TaskMatrixDet3X3(vm)
        }
    }
}

@Composable
fun TaskMatrixDet2X2(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull() {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.DET_2X2
        }!!
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val userInput = rememberSaveable { mutableStateOf("") }
        val matrix = Matrix(taskEntity.taskContent!!)
        val correctAnswer = matrix.determinant().toString()

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatrixView(matrix, true)

                Text("=", style = MaterialTheme.typography.titleLarge)

                NumberInputField(
                    userInput = userInput,
                    isAnswerCorrect = isAnswerCorrect,
                    correctAnswer = correctAnswer
                )

                CheckButton(
                    vm,
                    taskEntity,
                    userInput.value == matrix.determinant().toString(),
                    isAnswerCorrect
                )
            }

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}

@Composable
fun TaskMatrixDet3X3(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull() {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.DET_3X3
        }!!
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val userInput = rememberSaveable { mutableStateOf("") }
        val matrix = Matrix(taskEntity.taskContent!!)
        val correctAnswer = matrix.determinant().toString()

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatrixView(matrix, true)

                Text("=", style = MaterialTheme.typography.titleLarge)

                NumberInputField(
                    userInput = userInput,
                    isAnswerCorrect = isAnswerCorrect,
                    correctAnswer = correctAnswer
                )

                CheckButton(
                    vm,
                    taskEntity,
                    userInput.value == matrix.determinant().toString(),
                    isAnswerCorrect
                )
            }

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}