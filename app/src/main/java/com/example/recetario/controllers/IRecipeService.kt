package com.example.recetario.controllers

import com.example.recetario.http.Response
import com.example.recetario.models.Recipe
import retrofit2.http.GET

interface IRecipeService {
    @GET("recipes")
    suspend fun getAllRecipes(): Response<Recipe>
}