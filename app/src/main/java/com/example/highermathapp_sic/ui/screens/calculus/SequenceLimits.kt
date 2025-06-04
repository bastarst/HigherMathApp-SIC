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

@Composable
fun SequenceLimits(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Предел последовательности",
        onPrevious = "MatrixCramerRule",
        onNext = "FunctionLimits"
    ) {
        TheoreticalPart(TaskGroup.CALCULUS, "sequence_limits.txt")
        SequenceLimitsTask(vm)
    }
}

@Composable
fun SequenceLimitsTask(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.SEQUENCE_LIMIT
        }!!
        val list = TaskContentConverter.decodeList(taskEntity.taskContent!!)
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val userInput = rememberSaveable { mutableStateOf("") }

        TaskSection(
            task = "Найдите предел:"
        ) {
            LimitView(
                approachingValue = "∞",
                numerator = list[0].toString(),
                denominator = "x",
                userInput = userInput,
                isAnswerCorrect = isAnswerCorrect,
                correctAnswer = "0"
            )

            CheckButton(
                vm,
                taskEntity,
                userInput.value == "0",
                isAnswerCorrect
            )

            IsAnswerCorrect(isAnswerCorrect)
        }
    }
}