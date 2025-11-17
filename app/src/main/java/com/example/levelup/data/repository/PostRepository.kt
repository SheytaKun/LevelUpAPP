package com.example.levelup.data.repository


import com.example.levelup.data.model.Post
import com.example.levelup.data.remote.Retrofitinstance


class PostRepository {

    // Funcion de obtener los post desde la API

    suspend fun getPosts(): List<Post>{
        return Retrofitinstance.api.getPosts()
    }// fin suspend

}