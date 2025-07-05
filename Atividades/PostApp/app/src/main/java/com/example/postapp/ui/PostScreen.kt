package com.example.postapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postapp.data.models.Post
import com.example.postapp.viewmodel.PostViewModel

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Conteúdo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                isLoading = true
                viewModel.createPost(
                    title,
                    content,
                    onSuccess = {
                        Toast.makeText(context, "Post criado com sucesso", Toast.LENGTH_SHORT).show()
                        isLoading = false
                        title = ""
                        content = ""
                    },
                    onError = {
                        Toast.makeText(context, "Erro ao criar post", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Post")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(viewModel.posts) { post ->
                    PostItem(
                        post = post,
                        onDelete = { viewModel.deletePost(it) },
                        onEdit = { editingPost = it }
                    )
                }
            }
        }
    }

    editingPost?.let { postToEdit ->
        var editTitle by remember { mutableStateOf(postToEdit.title) }
        var editContent by remember { mutableStateOf(postToEdit.content) }

        AlertDialog(
            onDismissRequest = { editingPost = null },
            title = { Text(text = "Editar Post") },
            text = {
                Column {
                    TextField(
                        value = editTitle,
                        onValueChange = { editTitle = it },
                        label = { Text("Título") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = editContent,
                        onValueChange = { editContent = it },
                        label = { Text("Conteúdo") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updatePost(postToEdit.id, editTitle, editContent)
                        editingPost = null
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingPost = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}