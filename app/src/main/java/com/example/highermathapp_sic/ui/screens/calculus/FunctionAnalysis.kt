package com.example.highermathapp_sic.ui.screens.calculus

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.NumberInputField
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart
import kotlin.math.abs
import kotlin.math.round

@Composable
fun FunctionAnalysis(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Исследование функции",
        onPrevious = "FunctionLimits",
        onNext = "IndefiniteIntegrals"
    ) {
        TheoreticalPart(TaskGroup.CALCULUS, "function_analysis.txt")
        FunctionAnalysisTasks(vm)
    }
}

@Composable
fun FunctionAnalysisTasks(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity1 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.FUNCTION_ANALYSIS_1
        }!!
        val taskEntity2 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.FUNCTION_ANALYSIS_2
        }!!
        val list = TaskContentConverter.decodeList(taskEntity1.taskContent!!) +
                TaskContentConverter.decodeList(taskEntity2.taskContent!!)
        val isAnswerCorrect1 = taskEntity1.isAnswerCorrect
        val isAnswerCorrect2 = taskEntity2.isAnswerCorrect
        val userInput = rememberSaveable {
            List(3) { mutableStateOf("") }
        }
        val correctAnswer1 = round((-list[1].toDouble() / (list[0] * 2)) * 10) / 10
        val correctAnswer2 = round((-list[3].toDouble() / (list[2] * 2)) * 10) / 10
        val checkAnswer1 = listOf<Boolean>(
            userInput[0].value == "(-Inf, ${correctAnswer1})",
            userInput[1].value == "(${correctAnswer1}, Inf)"
        )

        TaskSection(
            task = "1. Найдите интервал возрастания и убывания (Вместо ∞ используете 'Inf'. Ответ окргулите до десятых): \n" +
                    "f(x) = ${list[0]}x²" + if (list[1] < 0) " - ${abs(list[1])}x" else " + ${abs(list[1])}x"
        ) {
            Text(
                text = "Убывает на:",
                style = MaterialTheme.typography.titleLarge
            )

            TextField(
                value = if (isAnswerCorrect1 == true) "(-Inf, ${correctAnswer1})" else userInput[0].value,
                onValueChange = { newAnswer -> userInput[0].value = newAnswer },
                textStyle = MaterialTheme.typography.titleLarge,
                readOnly = isAnswerCorrect1 == true
            )

            Text(
                text = "Возрастает на:",
                style = MaterialTheme.typography.titleLarge
            )

            TextField(
                value = if (isAnswerCorrect1 == true) "(${correctAnswer1}, Inf)" else userInput[1].value,
                onValueChange = { newAnswer -> userInput[1].value = newAnswer },
                textStyle = MaterialTheme.typography.titleLarge,
                readOnly = isAnswerCorrect1 == true
            )

            CheckButton(
                vm,
                taskEntity1,
                checkAnswer1.all { it },
                isAnswerCorrect1
            )

            IsAnswerCorrect(isAnswerCorrect1)
        }

        TaskSection(
            task = "2. Найдите экстремум функции (Ответ окргулите до десятых): \n" +
                    "f(x) = ${list[2]}x²" + if (list[3] < 0) " - ${abs(list[3])}x" else " + ${abs(list[3])}x"
        ) {
            Row {
                Text(
                    text = "Максимум при x = ",
                    style = MaterialTheme.typography.titleLarge
                )

                NumberInputField(
                    userInput = userInput[2],
                    isAnswerCorrect = isAnswerCorrect2,
                    correctAnswer = correctAnswer2.toString()
                )
            }

            CheckButton(
                vm,
                taskEntity2,
                userInput[2].value == correctAnswer2.toString(),
                isAnswerCorrect2
            )

            IsAnswerCorrect(isAnswerCorrect2)
        }
    }
}