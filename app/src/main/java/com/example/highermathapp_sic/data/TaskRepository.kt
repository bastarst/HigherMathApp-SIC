package com.example.highermathapp_sic.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(private val matrixDao: TaskDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val tasks: LiveData<List<TaskEntity>> = matrixDao.getAllMatrixEntity()

    fun addTask(task: TaskEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            matrixDao.insertMatrixTask(task)
        }
    }

    fun removeTask(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            matrixDao.deleteMatrixTask(id)
        }
    }

    fun updateAnswerCorrect(id: Int, isCorrect: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            matrixDao.updateIsAnswerCorrect(id, isCorrect)
        }
    }

    fun updateTask(id: Int, taskContent: String) {
        coroutineScope.launch(Dispatchers.IO) {
            matrixDao.updateTask(id, taskContent)
        }
    }
}