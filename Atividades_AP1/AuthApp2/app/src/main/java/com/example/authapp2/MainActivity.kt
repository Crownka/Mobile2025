package com.example.authapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authapp2.data.AuthRepository
import com.example.authapp2.ui.theme.AuthApp2Theme
import com.example.authapp2.ui.view.ForgotPasswordScreen
import com.example.authapp2.ui.view.HomeScreen
import com.example.authapp2.ui.view.LoginScreen
import com.example.authapp2.ui.view.RegisterScreen
import com.example.authapp2.viewmodel.AuthViewModel
import com.example.authapp2.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthApp2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository = AuthRepository()
                    val factory = AuthViewModelFactory(repository)
                    val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

                    val navController = rememberNavController()
                    val startDestination = if (viewModel.isUserLogged()) "home" else "login"

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login") {
                            LoginScreen(
                                viewModel = viewModel,
                                onLoginSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = { navController.navigate("register") },
                                onNavigateToForgotPassword = { navController.navigate("forgot_password") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                viewModel = viewModel,
                                onRegisterSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = { navController.popBackStack() }
                            )
                        }
                        composable("forgot_password") {
                            ForgotPasswordScreen(
                                viewModel = viewModel,
                                onNavigateToLogin = { navController.popBackStack() }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                viewModel = viewModel,
                                onLogoutSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}