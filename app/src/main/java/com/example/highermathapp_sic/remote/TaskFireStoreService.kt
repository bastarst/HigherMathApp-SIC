package com.example.highermathapp_sic.remote

import androidx.lifecycle.viewModelScope
import com.example.highermathapp_sic.data.task.TaskEntity
import com.example.highermathapp_sic.model.TaskGroup
import com.example.highermathapp_sic.model.TaskType
import com.example.highermathapp_sic.model.TaskViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TaskFireStoreService {
    private val uid = Firebase.auth.currentUser?.uid!!
    private val tasksRef = Firebase.firestore
        .collection("users")
        .document(uid)
        .collection("tasks")

    fun downloadTasksFromFireStoreOrInit(taskViewModel: TaskViewModel) {
        tasksRef.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    taskViewModel.viewModelScope.launch(Dispatchers.IO) {
                        taskViewModel.initialTaskDataBase()
                    }
                } else {
                    val tasks = result.mapNotNull { document ->
                        mapToTaskEntity(document.data)
                    }

                    taskViewModel.viewModelScope.launch(Dispatchers.IO) {
                        tasks.forEach {
                            taskViewModel.repository.addTask(it)
                        }
                    }
                }
            }
    }

    fun uploadTaskToFireStore(task: TaskEntity) {
        tasksRef
            .document(task.id.toString())
            .set(task.toMap())
    }

    private fun mapToTaskEntity(map: Map<String, Any?>): TaskEntity {
        return TaskEntity(
            id = (map["id"] as Long).toInt(),
            taskGroup = (map["taskGroup"] as? String)?.let { TaskGroup.valueOf(it) },
            taskType = (map["taskType"] as? String)?.let { TaskType.valueOf(it) },
            taskContent = map["taskContent"] as? String,
            isAnswerCorrect = map["isAnswerCorrect"] as? Boolean
        )
    }

    fun updateAnswerCorrectInFireStore(id: Int, isCorrect: Boolean) {
        tasksRef
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    tasksRef.document(doc.id)
                        .update("isAnswerCorrect", isCorrect)
                }
            }
    }

    fun updateTaskInFireStore(id: Int, newContent: String) {
        tasksRef
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    tasksRef.document(doc.id).update(
                        mapOf(
                            "taskContent" to newContent,
                            "isAnswerCorrect" to null
                        )
                    )
                }
            }
    }

    fun deleteAllTasksFromFireStore() {
        tasksRef.get()
            .addOnSuccessListener { documents ->
                for (doc in documents.documents) {
                    doc.reference.delete()
                }
            }
    }
}