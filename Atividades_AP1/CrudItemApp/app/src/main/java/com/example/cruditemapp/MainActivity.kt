package com.example.cruditemapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cruditemapp.data.Item
import com.example.cruditemapp.ui.theme.CrudItemAppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudItemAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CrudScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreen() {
    val db = Firebase.firestore
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var selectedItem by remember { mutableStateOf<Item?>(null) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        db.collection("items").addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val itemList = snapshot.documents.map { doc ->
                    val item = doc.toObject(Item::class.java)
                    item?.copy(id = doc.id) ?: Item()
                }
                items = itemList
            }
        }
    }

    if (showUpdateDialog && selectedItem != null) {
        UpdateItemDialog(
            item = selectedItem!!,
            onDismiss = { showUpdateDialog = false },
            onConfirm = { updatedTitle, updatedDescription ->
                db.collection("items").document(selectedItem!!.id)
                    .update(mapOf("title" to updatedTitle, "description" to updatedDescription))
                showUpdateDialog = false
            }
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newItem = hashMapOf(
                    "title" to title,
                    "description" to description
                )
                db.collection("items").add(newItem)
                title = ""
                description = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(items) { item ->
                ItemRow(
                    item = item,
                    onUpdateClick = {
                        selectedItem = item
                        showUpdateDialog = true
                    },
                    onDeleteClick = {
                        db.collection("items").document(item.id).delete()
                    }
                )
            }
        }
    }
}

@Composable
fun ItemRow(item: Item, onUpdateClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${item.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Description: ${item.description}", style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = onUpdateClick) {
                    Text("Update")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onDeleteClick) {
                    Text("Delete")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateItemDialog(item: Item, onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var updatedTitle by remember { mutableStateOf(item.title) }
    var updatedDescription by remember { mutableStateOf(item.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = updatedTitle,
                    onValueChange = { updatedTitle = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = updatedDescription,
                    onValueChange = { updatedDescription = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(updatedTitle, updatedDescription) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}