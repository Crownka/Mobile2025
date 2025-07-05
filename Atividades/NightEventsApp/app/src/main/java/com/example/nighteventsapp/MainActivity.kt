package com.example.nighteventsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nighteventsapp.ui.components.BottomNavigationBar
import com.example.nighteventsapp.ui.components.DrawerContent
import com.example.nighteventsapp.ui.components.TopBar
import com.example.nighteventsapp.ui.screens.EventDetailsScreen
import com.example.nighteventsapp.ui.screens.FavoritesScreen
import com.example.nighteventsapp.ui.screens.HomeScreen
import com.example.nighteventsapp.ui.screens.SubscribedEventsScreen
import com.example.nighteventsapp.ui.theme.NightEventsAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.nighteventsapp.ui.screens.sendNotification // Importação explícita para a função sendNotification
import com.example.nighteventsapp.ui.screens.createNotificationChannel // Importação explícita para a função createNotificationChannel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val isDarkTheme = remember { mutableStateOf(false) }
            val context = LocalContext.current

            // Cria o canal de notificação ao iniciar o aplicativo
            createNotificationChannel(context)

            NightEventsAppTheme(darkTheme = isDarkTheme.value) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        DrawerContent(navController) {
                            sendNotification(context, "Notificação enviada do menu lateral!")
                        }
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopBar(
                                    onThemeToggle = { isDarkTheme.value = !isDarkTheme.value },
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                )
                            },
                            bottomBar = { BottomNavigationBar(navController) }
                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable("home") { HomeScreen(navController, context) }
                                composable("events") { SubscribedEventsScreen(navController) }
                                composable("favorites") { FavoritesScreen(navController) }
                                composable("eventDetails/{eventId}") { backStackEntry ->
                                    val eventId = backStackEntry.arguments?.getString("eventId")
                                    EventDetailsScreen(eventId = eventId)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}