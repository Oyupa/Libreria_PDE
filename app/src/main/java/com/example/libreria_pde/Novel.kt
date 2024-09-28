package com.example.libreria_pde

data class Novel(
    val title: String,
    val details: String,
    val reviews: MutableList<String> = mutableListOf(),
    var isFavorite: Boolean = false
)
