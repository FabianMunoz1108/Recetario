package com.example.recetario.http

data class Response<Any>(
    var message: String = "",
    var data: List<Any>? = null
)