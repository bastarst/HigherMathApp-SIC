package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskEntity
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.MatrixEditor
import com.example.highermathapp_sic.ui.components.MatrixView
import com.example.highermathapp_sic.ui.components.NumberInputField
import androidx.compose.ui.graphics.Color
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@Composable
fun MatrixInverse(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Обратная матрица",
        onPrevious = "MatrixMinor",
        onNext = "MatrixCramerRule"
    ) {
        TheoreticalPart(TaskGroup.LINEAR_ALGEBRA, "matrixinverse.txt")
        TaskMatrixInverse(vm)
    }
}

@Composable
fun TaskMatrixInverse(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull() {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.INVERSE
        }!!
        val matrix = Matrix(taskEntity.taskContent!!)
        val actualMinors = matrix.allMinors()
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val userAnswers = rememberSaveable {
            List(4) { mutableStateOf("") }
        }
        val userDetAnswer = rememberSaveable { mutableStateOf("") }
        val userMatrix = remember { mutableStateOf(Matrix(2, 2)) }
        val userDetInverse = remember { mutableStateOf("") }
        val isDetCorrect = userDetAnswer.value == matrix.determinant().toString()
        val inverseDetCorrect = userDetInverse.value == matrix.determinant().toString()
        val isInverseMatrixCorrect = userMatrix.value.equals(matrix.inverseFromMinors())
        val isMinorsCorrect = userAnswers.withIndex().all { (index, answerState) ->
            answerState.value.toIntOrNull() == actualMinors[index]
        }
        val isUserAnswerCorrect = isDetCorrect && isInverseMatrixCorrect && isMinorsCorrect && inverseDetCorrect

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Найдите обратную матрицу:", style = MaterialTheme.typography.titleLarge)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "A = ",
                    style = MaterialTheme.typography.titleLarge
                )
                MatrixView(matrix)
            }

            DetInputField(userDetAnswer, matrix, isAnswerCorrect)

            MinorInputFields(userAnswers, matrix, isAnswerCorrect)

            InverseMatrixInput(
                userMatrix,
                userDetInverse,
                matrix.determinant().toString(),
                isAnswerCorrect,
                matrix.inverseFromMinors()
            )

            Row {
                Button(onClick = {
                    vm.updateAnswerCorrect(taskEntity.id, isUserAnswerCorrect)
                }) {
                    Text("Проверить", style = MaterialTheme.typography.titleLarge)
                }

                if(isAnswerCorrect == true) {
                    IconButton(onClick = {
                        vm.updateTask(taskEntity)
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Del")
                    }
                }
            }

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}

@Composable
fun MinorInputFields(userAnswers: List<MutableState<String>>, matrix: Matrix, isAnswerCorrect: Boolean?) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0..1) {
            for (j in 0..1) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "M(${i + 1}, ${j + 1}) = ",
                        style = MaterialTheme.typography.titleLarge
                    )

                    NumberInputField(
                        userInput = userAnswers[i * 2 + j],
                        isAnswerCorrect = isAnswerCorrect,
                        correctAnswer = matrix.minor(i, j).toString()
                    )
                }
            }
        }
    }
}

@Composable
fun DetInputField(userAnswer: MutableState<String>, matrix: Matrix, isAnswerCorrect: Boolean?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "det(A) = ",
            style = MaterialTheme.typography.titleLarge
        )

        NumberInputField(
            userInput = userAnswer,
            isAnswerCorrect = isAnswerCorrect,
            correctAnswer = matrix.determinant().toString()
        )
    }
}

@Composable
fun InverseMatrixInput(
    matrix: MutableState<Matrix>,
    det: MutableState<String>,
    correctDet: String,
    isAnswerCorrect: Boolean?,
    correctAnswer: Matrix
) {
    val color = MaterialTheme.colorScheme.onBackground

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "A^(-1)",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "=",
            style = MaterialTheme.typography.titleLarge
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "1",
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(
                modifier = Modifier
                    .width(44.dp),
                thickness = 2.dp,
                color = color
            )

            NumberInputField(
                userInput = det,
                isAnswerCorrect = isAnswerCorrect,
                correctAnswer = correctDet
            )
        }

        Icon(Icons.Default.Clear, contentDescription = "Mul")

        MatrixEditor(2, 2, matrix, isAnswerCorrect, correctAnswer)
    }
}