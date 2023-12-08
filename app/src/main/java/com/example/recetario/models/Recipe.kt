package com.example.recetario.models

import java.io.Serializable

data class Recipe(
    val title: String,
    val category: String,
    val minutes: Int,
    val instructions: List<String>,
    val ingredients: List<String>,
    val stars: Int,
    val imageUrl: String
): Serializable