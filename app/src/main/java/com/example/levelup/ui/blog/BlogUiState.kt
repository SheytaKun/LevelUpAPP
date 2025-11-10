package com.example.levelup.ui.blog

import com.example.levelup.data.model.Blog

data class BlogUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val posts: List<Blog> = emptyList()
)
