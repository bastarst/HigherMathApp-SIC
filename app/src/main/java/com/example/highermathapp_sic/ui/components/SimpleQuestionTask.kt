package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import com.example.highermathapp_sic.data.TaskGroup
import com.example.highermathapp_sic.data.TaskType
import com.example.highermathapp_sic.data.TaskViewModel

@Composable
fun SimpleQuestionTask(
    correctAnswer: String,
    vm: TaskViewModel,
    taskGroup: TaskGroup,
    taskType: TaskType,
    content: @Composable ColumnScope.() -> Unit
) {
    val taskList = vm.taskList.observeAsState(listOf())
    val taskEntity = taskList.value.lastOrNull() {
        it.taskGroup == taskGroup
        it.taskType == taskType
    }!!
    val userAnswer = rememberSaveable { mutableStateOf("") }
    val isCorrect = taskEntity.isAnswerCorrect

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        content()

        TextField(
            value = if (isCorrect == true) correctAnswer else userAnswer.value,
            onValueChange = { newAnswer -> userAnswer.value = newAnswer },
            textStyle = MaterialTheme.typography.titleLarge,
            readOnly = isCorrect == true
        )

        CheckButton(
            vm,
            taskEntity,
            userAnswer.value == correctAnswer,
            isCorrect
        )

        isCorrect?.let {
            Text(
                text = if (it) "✅ Ответ верный" else "❌ Ответ неверный"
            )
        }
    }
}