package com.example.highermathapp_sic.ui.screens.calculus

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.ui.components.BaseScreenLayout
import com.example.highermathapp_sic.ui.components.IntegralView
import com.example.highermathapp_sic.ui.components.SimpleQuestionTask
import com.example.highermathapp_sic.ui.components.TheoreticalPart

@Composable
fun IndefiniteIntegrals(
    navController: NavController,
    vm: TaskViewModel
) {
    BaseScreenLayout(
        navController = navController,
        title = "Неопределённый интеграл",
        onPrevious = "FunctionAnalysis",
        onNext = "DefiniteIntegrals"
    ) {
        TheoreticalPart(TaskGroup.CALCULUS, "indefinite_integrals.txt")
        IndefiniteIntegralsTasks(vm)
    }
}

@Composable
fun IndefiniteIntegralsTasks(vm: TaskViewModel) {
    val taskList = vm.taskList.observeAsState(listOf())

    if(!taskList.value.isEmpty()) {
        val taskEntity = taskList.value.lastOrNull {
            it.taskGroup == TaskGroup.CALCULUS
            it.taskType == TaskType.INDEFINITE_INTEGRALS
        }!!
        val list = TaskContentConverter.decodeList(taskEntity.taskContent!!)
        val num = list[0] + 1
        val correctAnswer = "x^${num} + C"

        Row(modifier = Modifier.padding(8.dp)) {
            SimpleQuestionTask(
                correctAnswer = correctAnswer,
                vm = vm,
                taskGroup = TaskGroup.CALCULUS,
                taskType = TaskType.INDEFINITE_INTEGRALS
            ) {
                Text(
                    text = "Решите (Для степени используйте '^'):",
                    style = MaterialTheme.typography.titleLarge
                )

                IntegralView("${num}x^${num - 1} dx")
            }
        }
    }
}