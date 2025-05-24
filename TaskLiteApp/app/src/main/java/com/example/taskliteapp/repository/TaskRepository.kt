package com.example.taskliteapp.repository

import com.example.taskliteapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskRepository {

    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Beber água"),
            Task(2, "Estudar"),
            Task(3, "Revisar código")
        )
    )

    val tasks: StateFlow<List<Task>> = _tasks

    fun toggleTask(id: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(isDone = !it.isDone) else it
        }
    }

    fun addTask(title: String) {
        // Encontra o maior ID existente ou começa com 0 se a lista estiver vazia
        val newId = (_tasks.value.maxByOrNull { it.id }?.id ?: 0) + 1
        val newTask = Task(id = newId, title = title)
        _tasks.value = _tasks.value + newTask
    }

    // Nova função para deletar uma tarefa [cite: 33]
    fun deleteTask(id: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }
}