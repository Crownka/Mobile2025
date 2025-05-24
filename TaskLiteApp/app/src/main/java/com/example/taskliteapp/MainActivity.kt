package com.example.taskliteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.taskliteapp.ui.TaskListScreen // Importação da TaskListScreen
import com.example.taskliteapp.ui.theme.TaskLiteAppTheme // Importação do tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskLiteAppTheme {
                // Uma superfície de contêiner usando a cor 'background' do tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskListScreen() // Chamada da sua tela de lista de tarefas
                }
            }
        }
    }
}