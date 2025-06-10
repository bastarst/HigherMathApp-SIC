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
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.CheckButton
import com.example.highermathapp_sic.ui.components.IntegralView
import com.example.highermathapp_sic.ui.components.IsAnswerCorrect
import com.example.highermathapp_sic.ui.components.NumberInputField
import com.example.highermathapp_sic.ui.components.TaskSection
import com.example.highermathapp_sic.ui.components.TheoreticalPart
import kotlin.math.round


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
        val taskEntity1 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.DEFINITE_INTEGRALS_1
        }!!
        val taskEntity2 = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.DEFINITE_INTEGRALS_2
        }!!
        val isAnswerCorrect1 = taskEntity1.isAnswerCorrect
        val isAnswerCorrect2 = taskEntity2.isAnswerCorrect
        val list = TaskContentConverter.decodeList(taskEntity1.taskContent!!) +
                TaskContentConverter.decodeList(taskEntity2.taskContent!!)
        val userInput = rememberSaveable {
            List(2) { mutableStateOf("") }
        }
        val correctAnswer1 = round((list[1].toDouble() * list[1] / 2 - list[0].toDouble() * list[0] / 2) * 10) / 10
        val correctAnswer2 = round((list[3].toDouble() * list[3] - list[2].toDouble() * list[2]) * 10) / 10

        TaskSection(
            task = "1. Решите (Ответ округлите до десятых):"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                IntegralView("x dx = ", list[0].toString(), list[1].toString())

                NumberInputField(
                    userInput = userInput[0],
                    isAnswerCorrect = isAnswerCorrect1,
                    correctAnswer = correctAnswer1.toString()
                )
            }

            CheckButton(
                vm,
                taskEntity1,
                userInput[0].value == correctAnswer1.toString(),
                isAnswerCorrect1
            )

            IsAnswerCorrect(isAnswerCorrect1)
        }

        TaskSection(
            task = "2. Найдите площадь под графиком y = 2x от ${list[2]} до ${list[3]} (Ответ округлите до десятых)"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("S = ", style = MaterialTheme.typography.titleLarge)

                NumberInputField(
                    userInput = userInput[1],
                    isAnswerCorrect = isAnswerCorrect2,
                    correctAnswer = correctAnswer2.toString()
                )
            }

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