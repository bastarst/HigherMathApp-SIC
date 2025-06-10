package com.example.highermathapp_sic.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.highermathapp_sic.data.TaskEntity
import com.example.highermathapp_sic.model.TaskViewModel

@Composable
fun CheckButton(
    vm: TaskViewModel,
    taskEntity: TaskEntity,
    checkAnswer: Boolean,
    isAnswerCorrect: Boolean?
) {
    Row {
        Button(
            onClick = { vm.updateAnswerCorrect(taskEntity.id, checkAnswer) },
            enabled = isAnswerCorrect != true
        ) {
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
}