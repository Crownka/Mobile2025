package com.example.planetapp.models

import com.example.planetapp.R

data class Planet(
    val id: Int,
    val name: String,
    val type: String,
    val galaxy: String,
    val distanceFromSun: String,
    val diameter: String,
    val characteristics: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

val planetList = listOf(
    Planet(
        id = 1,
        name = "Mercúrio",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "57,9 milhões de km",
        diameter = "4.879 km",
        characteristics = "O planeta mais próximo do Sol, não possui atmosfera significativa.",
        imageRes = R.drawable.mercurio
    ),
    Planet(
        id = 2,
        name = "Vênus",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "108,2 milhões de km",
        diameter = "12.104 km",
        characteristics = "Possui uma atmosfera densa e tóxica, com um efeito estufa descontrolado.",
        imageRes = R.drawable.venus
    ),
    Planet(
        id = 3,
        name = "Terra",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "149,6 milhões de km",
        diameter = "12.742 km",
        characteristics = "Suporta vida, possui água em estado líquido e uma atmosfera rica em oxigênio.",
        imageRes = R.drawable.terra
    ),
    Planet(
        id = 4,
        name = "Marte",
        type = "Terrestre",
        galaxy = "Via Láctea",
        distanceFromSun = "227,9 milhões de km",
        diameter = "6.779 km",
        characteristics = "Conhecido como o 'Planeta Vermelho' devido ao óxido de ferro em sua superfície.",
        imageRes = R.drawable.marte
    ),
    Planet(
        id = 5,
        name = "Júpiter",
        type = "Gigante Gasoso",
        galaxy = "Via Láctea",
        distanceFromSun = "778,5 milhões de km",
        diameter = "139.820 km",
        characteristics = "O maior planeta do Sistema Solar, com uma grande mancha vermelha.",
        imageRes = R.drawable.jupiter
    ),
    Planet(
        id = 6,
        name = "Saturno",
        type = "Gigante Gasoso",
        galaxy = "Via Láctea",
        distanceFromSun = "1,4 bilhão de km",
        diameter = "116.460 km",
        characteristics = "Famoso por seu impressionante sistema de anéis de gelo e rocha.",
        imageRes = R.drawable.saturno
    ),
    Planet(
        id = 7,
        name = "Urano",
        type = "Gigante de Gelo",
        galaxy = "Via Láctea",
        distanceFromSun = "2,9 bilhões de km",
        diameter = "50.724 km",
        characteristics = "Gira de lado em relação ao seu plano orbital e possui anéis tênues.",
        imageRes = R.drawable.urano
    ),
    Planet(
        id = 8,
        name = "Netuno",
        type = "Gigante de Gelo",
        galaxy = "Via Láctea",
        distanceFromSun = "4,5 bilhões de km",
        diameter = "49.244 km",
        characteristics = "O planeta mais distante do Sol, conhecido por seus ventos supersônicos.",
        imageRes = R.drawable.netuno
    ),
    Planet(
        id = 9,
        name = "Plutão",
        type = "Planeta Anão",
        galaxy = "Via Láctea",
        distanceFromSun = "5,9 bilhões de km",
        diameter = "2.377 km",
        characteristics = "Localizado no Cinturão de Kuiper, composto de gelo e rocha.",
        imageRes = R.drawable.plutao
    )
)