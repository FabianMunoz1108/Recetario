package com.example.recetario.controllers

import com.example.recetario.http.Response
import com.example.recetario.models.Auth
import com.example.recetario.models.User
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IAuthService {
    @Headers("Content-Type: application/json")
    @POST("users/token")
    suspend fun authenticateUser(@Body request: Auth): Response<User>
}