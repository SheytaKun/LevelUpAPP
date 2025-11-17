package com.example.levelup.remote
import com.example.levelup.data.model.Post
import retrofit2.http.GET

// en esta interfaz define el endpoint del HTTP
interface ApiService {

    // define la solicitud
    @GET(value="/posts")
    suspend fun getPosts():List<Post>

}