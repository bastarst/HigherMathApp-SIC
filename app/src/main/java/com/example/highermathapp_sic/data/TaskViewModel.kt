package com.example.highermathapp_sic.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter

class TaskViewModel(application: Application) : ViewModel() {
    val taskList: LiveData<List<TaskEntity>>
    private val repository: TaskRepository

    init {
        val taskDb = TaskDatabase.getInstance(application)
        val taskDao = taskDb.matrixDao()
        repository = TaskRepository(taskDao)
        taskList = repository.tasks
        taskList.observeForever { list ->
            if (list.isNullOrEmpty()) {
                initialTaskDataBase()
            }
        }
    }

    private fun initialTaskDataBase() {
        initialLADataBase()
    }

    private fun initialLADataBase() {
        val matrixAddString = TaskContentConverter.encode(Matrix(2, 2, randomize = true), Matrix(2, 2, randomize = true))
        addTask(matrixAddString, TaskGroup.LINEAR_ALGEBRA, TaskType.ADDITION)
    }

    fun updateTaskWithRandom2X2Matrices(id:Int) {
        val matrixAddString = TaskContentConverter.encode(Matrix(2, 2, randomize = true), Matrix(2, 2, randomize = true))
        repository.updateTask(id, matrixAddString)
    }

    fun addTask(taskContent: String, taskGroup: TaskGroup, taskType: TaskType) {
        val entity = TaskEntity(taskContent = taskContent, taskGroup = taskGroup, taskType = taskType)
        repository.addTask(entity)
    }

    fun deleteTask(id: Int) {
        repository.removeTask(id)
    }

    fun updateAnswerCorrect(id: Int, isCorrect: Boolean) {
        repository.updateAnswerCorrect(id, isCorrect)
    }
}