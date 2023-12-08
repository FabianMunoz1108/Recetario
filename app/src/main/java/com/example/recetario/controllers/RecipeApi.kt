package com.example.recetario.controllers

import com.example.recetario.http.Response
import com.example.recetario.models.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApi {
    private val api: IRecipeService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.1.16.60:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRecipeService::class.java)
    }

    suspend fun fetchRecipes(): Response<Recipe> {
        return api.getAllRecipes()
    }
}