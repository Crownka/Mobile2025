package com.example.planetapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.planetapp.models.Planet
import com.example.planetapp.models.planetList
import com.example.planetapp.ui.components.PlanetListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onPlanetSelected: (Planet) -> Unit
) {
    var planets by remember { mutableStateOf(planetList) }
    val favoritePlanets = planets.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favoritos") })
        }
    ) { innerPadding ->
        if (favoritePlanets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Você ainda não adicionou favoritos.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoritePlanets) { planet ->
                    PlanetListItem(
                        planet = planet,
                        onPlanetSelected = onPlanetSelected,
                        onFavoriteToggle = { toggledPlanet ->
                            val updatedPlanets = planets.map {
                                if (it.id == toggledPlanet.id) {
                                    it.copy(isFavorite = !it.isFavorite)
                                } else {
                                    it
                                }
                            }
                            planets = updatedPlanets
                        }
                    )
                }
            }
        }
    }
}