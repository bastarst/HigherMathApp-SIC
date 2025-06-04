package com.example.highermathapp_sic.ui.screens.calculus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IntegralView
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.NumberInputField
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart
import kotlin.collections.all


@Composable
fun DefiniteIntegrals(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Определённый интеграл",
        onPrevious = "IndefiniteIntegrals",
        onNext = "MainScreen"
    ) {
        TheoreticalPart(TaskGroup.CALCULUS, "definite_integrals.txt")
        DefiniteIntegralsTasks(vm)
    }
}

@Composable
fun DefiniteIntegralsTasks(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.DEFINITE_INTEGRALS
        }!!
        val isAnswerCorrect = taskEntity.isAnswerCorrect
        val list = TaskContentConverter.decodeList(taskEntity.taskContent!!).sorted()
        val userInput = rememberSaveable {
            List(2) { mutableStateOf("") }
        }
        val correctAnswer1: Int = list[1] * list[1] / 2 - list[0] * list[0] / 2
        val correctAnswer2: Int = list[3] * list[3] - list[2] * list[2]
        val checkAnswer = listOf<Boolean>(
            userInput[0].value == correctAnswer1.toString(),
            userInput[1].value == correctAnswer2.toString()
        )

        TaskSection(
            task = "1. Решите (В ответ впишите только целую часть):"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                IntegralView("x dx = ", list[0].toString(), list[1].toString())

                NumberInputField(
                    userInput = userInput[0],
                    isAnswerCorrect = isAnswerCorrect,
                    correctAnswer = correctAnswer1.toString()
                )
            }
        }

        TaskSection(
            task = "2. Найдите площадь под графиком y = 2x от ${list[2]} до ${list[3]}"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("S = ", style = MaterialTheme.typography.titleLarge)

                NumberInputField(
                    userInput = userInput[1],
                    isAnswerCorrect = isAnswerCorrect,
                    correctAnswer = correctAnswer2.toString()
                )
            }

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