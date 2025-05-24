package com.example.taskliteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskliteapp.model.Task
import com.example.taskliteapp.model.TaskFilter
import com.example.taskliteapp.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository() // Instância do repositório

    // Expõe o StateFlow de tarefas do repositório (todas as tarefas)
    val tasks: StateFlow<List<Task>> = repository.tasks

    // Novo StateFlow para o filtro selecionado
    private val _filter = MutableStateFlow(TaskFilter.ALL)
    val filter: StateFlow<TaskFilter> = _filter

    // StateFlow que combina a lista de tarefas e o filtro para produzir a lista filtrada
    val filteredTasks: StateFlow<List<Task>> = combine(tasks, _filter) { tasks, filter ->
        when (filter) {
            TaskFilter.ALL -> tasks
            TaskFilter.COMPLETED -> tasks.filter { it.isDone }
            TaskFilter.PENDING -> tasks.filter { !it.isDone }
        }
    }.stateIn(
        scope = viewModelScope, // Usa viewModelScope para o ciclo de vida do ViewModel
        started = SharingStarted.WhileSubscribed(5000), // Inicia enquanto houver assinantes
        initialValue = emptyList()
    )

    fun onAddTask(title: String) {
        repository.addTask(title)
    }

    fun onTaskChecked(id: Int) {
        repository.toggleTask(id)
    }

    // Nova função para deletar uma tarefa
    fun onDeleteTask(id: Int) {
        repository.deleteTask(id) // Será implementado no Repository no próximo passo
    }

    // Nova função para mudar o filtro
    fun onFilterSelected(filter: TaskFilter) {
        _filter.value = filter
    }
}