package com.example.nighteventsapp.models

import androidx.compose.runtime.mutableStateOf
import com.example.nighteventsapp.R // Certifique-se de que este import está correto

val eventList = listOf(
    Event(
        id = 1,
        title = "Conferência de Tecnologia 2024",
        description = "Tendências em tecnologia e inovação.",
        date = "2024-12-15",
        location = "Parque Tecnológico",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img1 // Certifique-se de ter essa imagem em res/drawable
    ),
    Event(
        id = 2,
        title = "Exposição de Arte Moderna",
        description = "Explorando a arte contemporânea com obras de artistas renomados.",
        date = "2024-12-20",
        location = "Museu de Arte",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img2 // Certifique-se de ter essa imagem em res/drawable
    ),
    Event(
        id = 3,
        title = "Festival de Música Independente",
        description = "O maior festival de música independente do ano com bandas locais e nacionais.",
        date = "2024-12-25",
        location = "Estádio Aberto",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img3 // Certifique-se de ter essa imagem em res/drawable
    ),
    Event(
        id = 4,
        title = "Encontro de Startups e Investidores",
        description = "Conecte-se com empreendedores e investidores para impulsionar seu negócio.",
        date = "2024-12-18",
        location = "Centro de Inovação",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img4 // Certifique-se de ter essa imagem em res/drawable
    ),
    Event(
        id = 5,
        title = "Festival Gastronômico de Verão",
        description = "Sabores e aromas únicos em um festival para toda a família.",
        date = "2024-12-30",
        location = "Praça Central",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img5 // Certifique-se de ter essa imagem em res/drawable
    ),
    Event(
        id = 6,
        title = "Congresso de Ciências e Inovação",
        description = "Avanços e descobertas científicas que estão moldando o futuro.",
        date = "2025-01-05",
        location = "Universidade Federal",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img6 // Certifique-se de ter essa imagem em res/drawable
    )
)