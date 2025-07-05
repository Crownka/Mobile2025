package com.example.postapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.postapp.data.models.Post

@Composable
fun PostItem(post: Post, onDelete: (Int) -> Unit, onEdit: (Post) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Deletar", color = Color.White)
                }
                Button(onClick = { onEdit(post) }) {
                    Text(text = "Editar")
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar Exclusão") },
            text = { Text(text = "Tem certeza que deseja excluir este post?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(post.id)
                        showDialog = false
                    }
                ) {
                    Text(text = "Sim", color = Color(0xFFD32F2F))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancelar", color = Color.Gray)
                }
            }
        )
    }
}