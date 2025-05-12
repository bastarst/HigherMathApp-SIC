package com.example.highermathapp_sic.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM matrix_entity")
    fun getAllMatrixEntity(): LiveData<List<TaskEntity>>

    @Insert
    fun insertMatrixTask(task: TaskEntity)

    @Query("DELETE FROM matrix_entity WHERE id = :id")
    fun deleteMatrixTask(id: Int)

    @Query("UPDATE matrix_entity SET isAnswerCorrect = :isCorrect WHERE id = :id")
    fun updateIsAnswerCorrect(id: Int, isCorrect: Boolean)

    @Query("UPDATE matrix_entity SET taskContent = :taskContent, isAnswerCorrect = null WHERE id = :id")
    fun updateTask(id: Int, taskContent: String)
}