package com.example.recetario.controllers

import com.example.recetario.http.Response
import com.example.recetario.models.Auth
import com.example.recetario.models.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApi {
    private val api: IAuthService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.1.16.60:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IAuthService::class.java)
    }
    suspend fun fetchUser(credentials: Auth): Response<User> {
        return api.authenticateUser(credentials)
    }
}