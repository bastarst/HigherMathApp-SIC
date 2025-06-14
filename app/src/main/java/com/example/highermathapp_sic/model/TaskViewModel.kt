package com.example.highermathapp_sic.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.highermathapp_sic.data.task.TaskDatabase
import com.example.highermathapp_sic.data.task.TaskEntity
import com.example.highermathapp_sic.data.task.TaskGroupStats
import com.example.highermathapp_sic.data.task.TaskGroupTotalStats
import com.example.highermathapp_sic.data.task.TaskRepository
import com.example.highermathapp_sic.data.task.TaskTypeStatus
import com.example.highermathapp_sic.remote.TaskFireStoreService

class TaskViewModel(
    application: Application,
    mode: Boolean
) : ViewModel() {
    val taskList: LiveData<List<TaskEntity>>
    val correctStats: LiveData<List<TaskGroupStats>>
    val taskGroupTotalStats: LiveData<List<TaskGroupTotalStats>>
    val taskTypeStatusList: LiveData<List<TaskTypeStatus>>
    val repository: TaskRepository
    private var initialized = false

    init {
        val taskDb = TaskDatabase.Companion.getInstance(application)
        val taskDao = taskDb.taskDao()
        repository = TaskRepository(taskDao)
        taskList = repository.tasks
        correctStats = taskDao.getCorrectAnswersCountPerGroup()
        taskGroupTotalStats = taskDao.getTotalTasksPerGroup()
        taskTypeStatusList = taskDao.getAllTaskTypeStatus()
//        taskList.observeForever { list ->
//            if (!initialized && list.isNullOrEmpty()) {
//                initialTaskDataBase()
//                initialized = true
//            }
//        }
    }

    suspend fun initialTaskDataBase() {
        initialLADataBase()
        initialCalculusDataBase()
    }

    private suspend fun initialLADataBase() {
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

    private suspend fun initialCalculusDataBase() {
        addCalculusTask(TaskType.SEQUENCE_LIMIT)
        addCalculusTask(TaskType.FUNCTIONAL_LIMIT_1)
        addCalculusTask(TaskType.FUNCTIONAL_LIMIT_2)
        addCalculusTask(TaskType.FUNCTION_ANALYSIS_1)
        addCalculusTask(TaskType.FUNCTION_ANALYSIS_2)
        addCalculusTask(TaskType.INDEFINITE_INTEGRALS)
        addCalculusTask(TaskType.DEFINITE_INTEGRALS_1)
        addCalculusTask(TaskType.DEFINITE_INTEGRALS_2)
    }

    fun updateTask(taskEntity: TaskEntity) {
        val updatedTaskContent = when (taskEntity.taskGroup!!) {
            TaskGroup.LINEAR_ALGEBRA -> TaskGenerator.generateLinearAlgebraTask(taskEntity.taskType!!)
            TaskGroup.CALCULUS -> TaskGenerator.generateCalculusTask(taskEntity.taskType!!)
        }
        repository.updateTask(taskEntity.id, updatedTaskContent)
        TaskFireStoreService.updateTaskInFireStore(taskEntity.id, updatedTaskContent)
    }

    suspend fun addTask(taskContent: String, taskGroup: TaskGroup, taskType: TaskType) {
        val entity = TaskEntity(
            taskContent = taskContent,
            taskGroup = taskGroup,
            taskType = taskType
        )
        val task = repository.addTask(entity)
        TaskFireStoreService.uploadTaskToFireStore(task)
    }

    fun updateAnswerCorrect(id: Int, isCorrect: Boolean) {
        repository.updateAnswerCorrect(id, isCorrect)
        TaskFireStoreService.updateAnswerCorrectInFireStore(id, isCorrect)
    }

    fun clearAllTasks() {
        repository.clearAllTasks()
    }

    private suspend fun addLATask(taskType: TaskType) {
        addTask(TaskGenerator.generateLinearAlgebraTask(taskType), TaskGroup.LINEAR_ALGEBRA, taskType)
    }

    private suspend fun addCalculusTask(taskType: TaskType) {
        addTask(TaskGenerator.generateCalculusTask(taskType), TaskGroup.CALCULUS, taskType)
    }
}