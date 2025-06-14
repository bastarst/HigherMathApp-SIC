package com.example.highermathapp_sic.data.task

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(private val taskDao: TaskDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val tasks: LiveData<List<TaskEntity>> = taskDao.getAllTaskEntity()

    suspend fun addTask(task: TaskEntity): TaskEntity {
        var newId = taskDao.insertTask(task).toInt()
        return task.copy(id = newId)
    }

    fun removeTask(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(id)
        }
    }

    fun updateAnswerCorrect(id: Int, isCorrect: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            taskDao.updateIsAnswerCorrect(id, isCorrect)
        }
    }

    fun updateTask(id: Int, taskContent: String) {
        coroutineScope.launch(Dispatchers.IO) {
            taskDao.updateTask(id, taskContent)
        }
    }

    fun clearAllTasks() {
        coroutineScope.launch(Dispatchers.IO) {
            taskDao.clearAllTasks()
        }
    }
}