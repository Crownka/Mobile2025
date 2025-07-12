package com.example.authapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authapp.remote.AuthApi
import com.example.authapp.remote.RetrofitClient
import com.example.authapp.repository.AuthRepository
import com.example.authapp.ui.screen.HomeScreen
import com.example.authapp.ui.screen.LoginScreen
import com.example.authapp.ui.screen.RegisterScreen
import com.example.authapp.ui.theme.AuthAppTheme
import com.example.authapp.viewmodel.AuthViewModel
import com.example.authapp.viewmodel.AuthState

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthAppTheme {
                val context = LocalContext.current
                val authApi = RetrofitClient.instance.create(AuthApi::class.java)
                val authRepository = AuthRepository(authApi, context)
                val authViewModel: AuthViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return AuthViewModel(authRepository) as T
                        }
                    }
                )
                AuthNavigation(viewModel = authViewModel)
            }
        }
    }
}

@Composable
fun AuthNavigation(viewModel: AuthViewModel) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkAuth()
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onAuthenticated = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}