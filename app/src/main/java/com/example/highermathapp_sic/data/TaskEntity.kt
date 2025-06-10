package com.example.highermathapp_sic.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType

@Entity(tableName = "task_entity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskGroup: TaskGroup? = null,
    var taskType: TaskType? = null,
    val taskContent: String? = null,
    val isAnswerCorrect: Boolean? = null
)