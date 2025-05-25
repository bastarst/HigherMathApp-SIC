package com.example.highermathapp_sic.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TaskDao {
    @Query("SELECT * FROM matrix_entity")
    fun getAllTaskEntity(): LiveData<List<TaskEntity>>

    @Insert
    fun insertTask(task: TaskEntity)

    @Query("DELETE FROM matrix_entity WHERE id = :id")
    fun deleteTask(id: Int)

    @Query("UPDATE matrix_entity SET isAnswerCorrect = :isCorrect WHERE id = :id")
    fun updateIsAnswerCorrect(id: Int, isCorrect: Boolean)

    @Query("UPDATE matrix_entity SET taskContent = :taskContent, isAnswerCorrect = null WHERE id = :id")
    fun updateTask(id: Int, taskContent: String)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'matrix_entity'")
    fun resetIdSequence()

    @Query("DELETE FROM matrix_entity")
    fun clearAll()

    @Transaction
    fun clearAllTasks() {
        clearAll()
        resetIdSequence()
    }
}