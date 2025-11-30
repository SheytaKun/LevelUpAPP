package com.example.levelup.remote

import com.example.levelup.data.model.NoticiasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiaApiService {

    @GET("everything")
    suspend fun getGamingNews(
        @Query("q")
        query: String = "videojuegos OR videojuego OR gaming OR gamer OR \"video game\" OR \"video games\" OR esports",

        @Query("language")
        language: String = "es",

        @Query("sortBy")
        sortBy: String = "publishedAt",

        @Query("pageSize")
        pageSize: Int = 20,

        @Query("domains")
        domains: String = "3djuegos.com,vandal.elespanol.com,levelup.com,hobbyconsolas.com,ign.com,gamespot.com,pcgamer.com",

        @Query("apiKey")
        apiKey: String
    ): NoticiasResponse
}