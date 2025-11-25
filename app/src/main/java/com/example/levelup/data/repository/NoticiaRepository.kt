package com.example.levelup.data.repository

import com.example.levelup.BuildConfig
import com.example.levelup.data.model.NoticiasArticle
import com.example.levelup.data.remote.RetrofitNoticia

class NoticiaRepository {
    suspend fun getGamingNews(): List<NoticiasArticle> {
        val response = RetrofitNoticia.api.getGamingNews(
            apiKey = BuildConfig.NEWS_API_KEY
        )
        return response.articles ?: emptyList()
    }
}