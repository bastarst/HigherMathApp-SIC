package com.example.highermathapp_sic.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.highermathapp_sic.model.Matrix
import com.example.highermathapp_sic.model.TaskContentConverter
import com.example.highermathapp_sic.model.TaskGenerator

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
        addLATask(TaskType.ADDITION)
        addLATask(TaskType.SUBTRACTION)
        addLATask(TaskType.MULTIPLICATION_BY_NUM)
        addLATask(TaskType.MULTIPLICATION_BY_MATRIX_1)
        addLATask(TaskType.MULTIPLICATION_BY_MATRIX_2)
        addLATask(TaskType.DET_2X2)
        addLATask(TaskType.DET_3X3)
        addLATask(TaskType.MINOR)
        addLATask(TaskType.INVERSE)
        addLATask(TaskType.CRAMER_RULE)
    }

    fun updateTask(taskEntity: TaskEntity) {
        val updatedTaskContent = when (taskEntity.taskGroup!!) {
            TaskGroup.LINEAR_ALGEBRA -> TaskGenerator.generateLinearAlgebraTask(taskEntity.taskType!!)
            TaskGroup.CALCULUS -> TODO()
            TaskGroup.DIFF_EQ -> TODO()
        }
        repository.updateTask(taskEntity.id, updatedTaskContent)
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

    fun clearAllTasks() {
        repository.clearAllTasks()
        initialTaskDataBase()
    }

    private fun addLATask(taskType: TaskType) {
        addTask(TaskGenerator.generateLinearAlgebraTask(taskType), TaskGroup.LINEAR_ALGEBRA, taskType)
    }
}