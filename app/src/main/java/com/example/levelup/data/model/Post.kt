package com.example.levelup.data.model
// Clase que represnte los valores obtenidos desde la API


data class Post(
    val userId :Int,
    val id:Int,
    val title: String,
    val body: String
)