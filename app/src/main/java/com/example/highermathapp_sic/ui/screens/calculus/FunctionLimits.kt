package com.example.highermathapp_sic.ui.screens.calculus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.LimitView
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart
import kotlin.math.abs

@Composable
fun FunctionLimits(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Предел функции",
        onPrevious = "SequenceLimits",
        onNext = "FunctionAnalysis"
    ) {
        TheoreticalPart(TaskGroup.CALCULUS, "function_limits.txt")
        FunctionLimitsTasks(vm)
    }
}

@Composable
fun FunctionLimitsTasks(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity1 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.FUNCTIONAL_LIMIT_1
        }!!
        val taskEntity2 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.FUNCTIONAL_LIMIT_2
        }!!
        val list = TaskContentConverter.decodeList(taskEntity1.taskContent!!) +
                TaskContentConverter.decodeList(taskEntity2.taskContent!!)
        val isAnswerCorrect1 = taskEntity1.isAnswerCorrect
        val isAnswerCorrect2 = taskEntity2.isAnswerCorrect
        val userInput = rememberSaveable {
            List(2) { mutableStateOf("") }
        }
        val numerator = "x²" +
                (if (list[1] < 0) " - " else " + ") +
                        abs(list[1]).toString()
        val correctAnswer1 = list[0] * list[0] + list[1]
        val correctAnswer2 = list[2] + 1

        TaskSection(
            task = "Найдите предел:"
        ) {
            LimitView(
                approachingValue = list[0].toString(),
                numerator = numerator,
                userInput = userInput[0],
                isAnswerCorrect = isAnswerCorrect1,
                correctAnswer = correctAnswer1.toString()
            )

            CheckButton(
                vm,
                taskEntity1,
                userInput[0].value == correctAnswer1.toString(),
                isAnswerCorrect1
            )

            IsAnswerCorrect(isAnswerCorrect1)

            LimitView(
                approachingValue = list[2].toString(),
                numerator = "x² - 1",
                denominator = "x - 1",
                userInput = userInput[1],
                isAnswerCorrect = isAnswerCorrect2,
                correctAnswer = correctAnswer2.toString()
            )

            CheckButton(
                vm,
                taskEntity2,
                userInput[1].value == correctAnswer2.toString(),
                isAnswerCorrect2
            )

            IsAnswerCorrect(isAnswerCorrect2)
        }
    }
}