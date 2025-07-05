package com.example.zooapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zooapp.models.Animal
import com.example.zooapp.models.animalList
import com.example.zooapp.ui.components.AnimalListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAnimalSelected: (Animal) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredAnimals = remember(searchQuery) {
        animalList.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ZooApp") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar Animais") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredAnimals) { animal ->
                    AnimalListItem(
                        animal = animal,
                        onAnimalSelected = onAnimalSelected
                    )
                }
            }
        }
    }
}