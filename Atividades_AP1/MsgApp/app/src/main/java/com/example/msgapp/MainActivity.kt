package com.example.msgapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msgapp.ui.theme.MsgAppTheme
import com.example.msgapp.ui.view.ChatScreen
import com.example.msgapp.ui.view.RoomSelector
import com.example.msgapp.viewmodel.MsgViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        setContent {
            MsgAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MsgApp()
                }
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun MsgApp(
    vm: MsgViewModel = viewModel(),
    auth: FirebaseAuth = Firebase.auth
) {
    val navController = rememberNavController()
    var userId by remember { mutableStateOf(auth.currentUser?.uid) }
    var userName by remember { mutableStateOf("Anônimo") }

    LaunchedEffect(auth) {
        if (auth.currentUser == null) {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userId = task.result?.user?.uid
                    userName = "User-${userId?.takeLast(4)}"
                }
            }
        } else {
            userId = auth.currentUser?.uid
            userName = "User-${userId?.takeLast(4)}"
        }
    }

    val messages by vm.messages.collectAsState()

    NavHost(navController = navController, startDestination = "room_selector") {
        composable("room_selector") {
            RoomSelector { roomName ->
                vm.joinRoom(roomName)
                navController.navigateToChat(roomName)
            }
        }
        composable("chat/{roomName}") { backStackEntry ->
            val roomName = backStackEntry.arguments?.getString("roomName") ?: "geral"
            ChatScreen(
                userId = userId ?: "",
                messages = messages,
                onSend = { text -> vm.sendMessage(userId ?: "", userName, text) },
                currentRoom = roomName,
                onLeaveRoom = {
                    vm.leaveRoom()
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavController.navigateToChat(roomName: String) {
    this.navigate("chat/$roomName")
}