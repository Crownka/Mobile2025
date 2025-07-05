package com.example.planetapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.planetapp.models.planetList
import com.example.planetapp.ui.components.BottomNavigationBar
import com.example.planetapp.ui.screens.DetailsScreen
import com.example.planetapp.ui.screens.FavoritesScreen
import com.example.planetapp.ui.screens.HomeScreen

sealed class BottomBarScreen(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomBarScreen("home", Icons.Default.Home, "Home")
    object Favorites : BottomBarScreen("favorites", Icons.Default.Favorite, "Favoritos")
}

@Composable
fun NavGraph(
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomBarScreen.Home.route) {
                HomeScreen(
                    onPlanetSelected = { planet ->
                        navController.navigate("details/${planet.id}")
                    },
                    onSettingsClick = onSettingsClick,
                    onHelpClick = onHelpClick
                )
            }
            composable(BottomBarScreen.Favorites.route) {
                FavoritesScreen(
                    onPlanetSelected = { planet ->
                        navController.navigate("details/${planet.id}")
                    }
                )
            }
            composable(
                route = "details/{planetId}",
                arguments = listOf(navArgument("planetId") { type = NavType.IntType })
            ) { backStackEntry ->
                val planetId = backStackEntry.arguments?.getInt("planetId")
                val selectedPlanet = planetList.first { it.id == planetId }
                DetailsScreen(
                    planet = selectedPlanet,
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}