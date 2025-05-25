package com.example.highermathapp_sic.ui.screens.linearalgebra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.MatrixEditor
import com.example.highermathapp_sic.ui.components.MatrixInput
import com.example.highermathapp_sic.ui.components.NumberInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixCramerRule(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "СЛАУ. Метод Крамера"
    ) {
        Text("TEXT")
        TaskCramerRule(vm)
    }
}

@Composable
fun TaskCramerRule(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull() {
            it.taskGroup == TaskGroup.LINEAR_ALGEBRA
            it.taskType == TaskType.CRAMER_RULE
        }!!
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val slau = taskEntity.taskContent!!.split(",")
        val (matrixA, matrixB, matrixC) = TaskContentConverter.decodeCramerRule(taskEntity.taskContent!!)
        val matrixList = listOf<Matrix>(matrixA, matrixB, matrixC)
        val detA = matrixA.determinant().toString()
        val detB = matrixB.determinant().toString()
        val detC = matrixC.determinant().toString()
        val userAnswers = rememberSaveable {
            List(7) { mutableStateOf("") }
        }
        val usersMatrix = remember {
            List(3) { mutableStateOf(Matrix(2, 2))}
        }
        val correctAnswer = listOf<Boolean>(
            matrixA.equals(usersMatrix[0].value),
            matrixB.equals(usersMatrix[1].value),
            matrixC.equals(usersMatrix[2].value),
            detA == userAnswers[0].value,
            detB == userAnswers[1].value,
            detC == userAnswers[2].value,
            userAnswers[3].value == detB,
            userAnswers[4].value == detA,
            userAnswers[5].value == detC,
            userAnswers[6].value == detA
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Решите СЛАУ (Ответ не сокращать):", style = MaterialTheme.typography.titleLarge)

            SLAUView(slau)

            for (i in 0..2) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "D${i} = ",
                        style = MaterialTheme.typography.titleLarge
                    )

                    MatrixEditor(2, 2, usersMatrix[i], isAnswerCorrect, matrixList[i])

                    Text(
                        text = "=",
                        style = MaterialTheme.typography.titleLarge
                    )

                    NumberInputField(
                        userInput = userAnswers[i],
                        isAnswerCorrect = isAnswerCorrect,
                        correctAnswer = matrixList[i].determinant().toString()
                    )
                }
            }

            for (i in 0..1) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (i == 0) "x" else "y",
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
                        NumberInputField(
                            userInput = userAnswers[i * 2 + 3],
                            isAnswerCorrect = isAnswerCorrect,
                            correctAnswer = if (i == 0) detB else detC
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .width(44.dp),
                            thickness = 2.dp,
                            color = Color.Black
                        )

                        NumberInputField(
                            userInput = userAnswers[i * 2 + 4],
                            isAnswerCorrect = isAnswerCorrect,
                            correctAnswer = detA
                        )
                    }
                }
            }

            CheckButton(
                vm,
                taskEntity,
                correctAnswer.all { it },
                isAnswerCorrect
            )

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}

@Composable
fun SLAUView(list: List<String>) {
    Box(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 2.dp.toPx()
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(16f, 0f),
                    strokeWidth = 2.dp.toPx()
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(16f, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
            .padding(6.dp)
    ) {
        Column {
            for(i in 0..1) {
                val x = list[i * 3]
                val y = list[i * 3 + 1].toInt()
                val c = list[i * 3 + 2]

                Row {
                    Text(
                        text = x + "x ${formatSigned(y)}y = " + c,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

fun formatSigned(value: Int): String {
    return if (value >= 0) "+$value" else "$value"
}