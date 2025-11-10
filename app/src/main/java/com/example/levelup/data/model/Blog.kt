package com.example.levelup.data.model

data class Blog (
    val id: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val author: String,
    val date: String,
    val imageUrl: String? = null,
    val externalUrl: String? = null
)