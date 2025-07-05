package com.example.tasksmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.tasksmanagerapp.ui.theme.TasksManagerAppTheme
import com.example.tasksmanagerapp.components.TasksScreen // <-- ADICIONADO AQUI!

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksManagerAppTheme {
                TasksScreen()
            }
        }
    }
}