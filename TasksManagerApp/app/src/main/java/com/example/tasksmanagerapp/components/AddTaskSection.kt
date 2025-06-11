package com.example.tasksmanagerapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.tasksmanagerapp.TaskCategory
import com.example.tasksmanagerapp.TaskPriority
import com.example.tasksmanagerapp.components.DropdownMenuBox


@Composable
fun AddTaskSection(onAddTask: (String, TaskCategory, TaskPriority) -> Unit) {
    var taskName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(TaskCategory.CASA) }
    var selectedPriority by remember { mutableStateOf(TaskPriority.MEDIA) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Nova tarefa") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuBox(
                label = "Categoria",
                options = TaskCategory.values().map { it.name }
            ) {
                selectedCategory = TaskCategory.valueOf(it)
            }
            DropdownMenuBox(
                label = "Prioridade",
                options = TaskPriority.values().map { it.name }
            ) {
                selectedPriority = TaskPriority.valueOf(it)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (taskName.isNotBlank()) {
                    onAddTask(taskName, selectedCategory, selectedPriority)
                    taskName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Tarefa")
        }
    }
}