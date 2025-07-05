package com.example.zooapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zooapp.models.animalList
import com.example.zooapp.ui.screens.AnimalScreen
import com.example.zooapp.ui.screens.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onAnimalSelected = { animal ->
                navController.navigate("animal/${animal.id}")
            })
        }
        composable(
            route = "animal/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getInt("animalId")
            val selectedAnimal = animalList.first { it.id == animalId }
            AnimalScreen(
                animal = selectedAnimal,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}