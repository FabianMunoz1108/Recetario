package com.example.recetario.controllers

import com.example.recetario.http.Response
import com.example.recetario.models.Recipe
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRecipeService {
    @GET("recipes")
    suspend fun getRecipes(): Response<Recipe>

    @POST("recipes/generate")
    suspend fun postRecipe(@Query("ingredients") ingredients: String): Response<Recipe>
}