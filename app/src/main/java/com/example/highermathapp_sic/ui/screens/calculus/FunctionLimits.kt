package com.example.highermathapp_sic.ui.screens.calculus

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
        val taskEntity = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.FUNCTIONAL_LIMIT
        }!!
        val list = TaskContentConverter.decodeList(taskEntity.taskContent!!)
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val userInput = rememberSaveable {
            List(2) { mutableStateOf("") }
        }
        val numerator = "x²" +
                (if (list[1] < 0) " - " else " + ") +
                        abs(list[1]).toString()
        val correctAnswer1 = list[0] * list[0] + list[1]
        val correctAnswer2 = list[2] + 1
        val checkAnswer = listOf<Boolean>(
            userInput[0].value == correctAnswer1.toString(),
            userInput[1].value == correctAnswer2.toString()
        )

        TaskSection(
            task = "Найдите предел:"
        ) {
            LimitView(
                approachingValue = list[0].toString(),
                numerator = numerator,
                userInput = userInput[0],
                isAnswerCorrect = isAnswerCorrect,
                correctAnswer = correctAnswer1.toString()
            )

            LimitView(
                approachingValue = list[2].toString(),
                numerator = "x² - 1",
                denominator = "x - 1",
                userInput = userInput[1],
                isAnswerCorrect = isAnswerCorrect,
                correctAnswer = correctAnswer2.toString()
            )

            CheckButton(
                vm,
                taskEntity,
                checkAnswer.all { it },
                isAnswerCorrect
            )

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}