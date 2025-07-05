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
import com.example.postapp.viewmodel.PostViewModel

@Composable
fun UserScreen(viewModel: PostViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                isLoading = true
                viewModel.createUser(
                    name,
                    email,
                    onSuccess = {
                        Toast.makeText(context, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                        isLoading = false
                        name = ""
                        email = ""
                    },
                    onError = {
                        Toast.makeText(context, "Erro ao criar usuário", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Usuário")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(viewModel.users) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nome: ${user.name}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}