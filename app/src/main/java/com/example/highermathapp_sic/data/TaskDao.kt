package com.example.highermathapp_sic.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType

data class TaskGroupStats(
    val taskGroup: TaskGroup,
    val correctCount: Int
)

data class TaskGroupTotalStats(
    val taskGroup: TaskGroup,
    val totalCount: Int
)

data class TaskTypeStatus(
    val taskType: TaskType,
    val isAnswerCorrect: Boolean?
)

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_entity")
    fun getAllTaskEntity(): LiveData<List<TaskEntity>>

    @Insert
    fun insertTask(task: TaskEntity)

    @Query("DELETE FROM task_entity WHERE id = :id")
    fun deleteTask(id: Int)

    @Query("UPDATE task_entity SET isAnswerCorrect = :isCorrect WHERE id = :id")
    fun updateIsAnswerCorrect(id: Int, isCorrect: Boolean)

    @Query("UPDATE task_entity SET taskContent = :taskContent, isAnswerCorrect = null WHERE id = :id")
    fun updateTask(id: Int, taskContent: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'matrix_entity'")
    fun resetIdSequence()

    @Query("DELETE FROM task_entity")
    fun clearAll()

    @Transaction
    fun clearAllTasks() {
        clearAll()
        resetIdSequence()
    }

    @Query("""
        SELECT taskGroup, COUNT(*) as correctCount 
        FROM task_entity 
        WHERE isAnswerCorrect = 1 
        GROUP BY taskGroup
    """)
    fun getCorrectAnswersCountPerGroup(): LiveData<List<TaskGroupStats>>

    @Query("""
        SELECT taskGroup, COUNT(*) as totalCount 
        FROM task_entity 
        GROUP BY taskGroup
    """)
    fun getTotalTasksPerGroup(): LiveData<List<TaskGroupTotalStats>>

    @Query("""
        SELECT taskType, isAnswerCorrect
        FROM task_entity
    """)
    fun getAllTaskTypeStatus(): LiveData<List<TaskTypeStatus>>
}