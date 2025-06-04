package com.example.highermathapp_sic.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.highermathapp_sic.model.TaskGenerator

class TaskViewModel(application: Application) : ViewModel() {
    val taskList: LiveData<List<TaskEntity>>
    val correctStats: LiveData<List<TaskGroupStats>>
    val taskGroupTotalStats: LiveData<List<TaskGroupTotalStats>>
    private val repository: TaskRepository

    init {
        val taskDb = TaskDatabase.getInstance(application)
        val taskDao = taskDb.taskDao()
        repository = TaskRepository(taskDao)
        taskList = repository.tasks
        correctStats = taskDao.getCorrectAnswersCountPerGroup()
        taskGroupTotalStats = taskDao.getTotalTasksPerGroup()
        taskList.observeForever { list ->
            if (list.isNullOrEmpty()) {
                initialTaskDataBase()
            }
        }
    }

    private fun initialTaskDataBase() {
        initialLADataBase()
        initialCalculusDataBase()
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

    private fun initialCalculusDataBase() {
        addCalculusTask(TaskType.SEQUENCE_LIMIT)
        addCalculusTask(TaskType.FUNCTIONAL_LIMIT)
        addCalculusTask(TaskType.FUNCTION_ANALYSIS)
        addCalculusTask(TaskType.INDEFINITE_INTEGRALS)
        addCalculusTask(TaskType.DEFINITE_INTEGRALS)
    }

    fun updateTask(taskEntity: TaskEntity) {
        val updatedTaskContent = when (taskEntity.taskGroup!!) {
            TaskGroup.LINEAR_ALGEBRA -> TaskGenerator.generateLinearAlgebraTask(taskEntity.taskType!!)
            TaskGroup.CALCULUS -> TaskGenerator.generateCalculusTask(taskEntity.taskType!!)
        }
        repository.updateTask(taskEntity.id, updatedTaskContent)
    }

    fun addTask(taskContent: String, taskGroup: TaskGroup, taskType: TaskType) {
        val entity = TaskEntity(taskContent = taskContent, taskGroup = taskGroup, taskType = taskType)
        repository.addTask(entity)
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

    private fun addCalculusTask(taskType: TaskType) {
        addTask(TaskGenerator.generateCalculusTask(taskType), TaskGroup.CALCULUS, taskType)
    }
}