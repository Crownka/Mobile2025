package com.example.zooapp.models

import com.example.zooapp.R

data class Animal(
    val id: Int,
    val name: String,
    val species: String,
    val imageRes: Int,
    val description: String,
    val curiosities: String,
    var isFavorite: Boolean = false
)

val animalList = listOf(
    Animal(
        id = 1,
        name = "Cachorro",
        species = "Canis lupus familiaris",
        imageRes = R.drawable.ic_launcher_background,
        description = "O cão é um dos animais mais antigos domesticados pelo homem.",
        curiosities = "Os cães têm um olfato cerca de 40 vezes mais potente que o dos humanos."
    ),
    Animal(
        id = 2,
        name = "Gato",
        species = "Felis catus",
        imageRes = R.drawable.ic_launcher_background,
        description = "O gato doméstico é conhecido por sua agilidade e independência.",
        curiosities = "Gatos passam cerca de 70% do dia dormindo."
    ),
    Animal(
        id = 3,
        name = "Elefante",
        species = "Loxodonta africana",
        imageRes = R.drawable.ic_launcher_background,
        description = "Elefantes são os maiores animais terrestres vivos.",
        curiosities = "Elefantes têm uma memória excepcional, sendo capazes de reconhecer amigos após anos de separação."
    ),
    Animal(
        id = 4,
        name = "Tigre",
        species = "Panthera tigris",
        imageRes = R.drawable.ic_launcher_background,
        description = "O tigre é o maior felino do mundo, conhecido por suas listras únicas.",
        curiosities = "As listras de um tigre são como impressões digitais; não existem dois tigres com o mesmo padrão."
    )
)