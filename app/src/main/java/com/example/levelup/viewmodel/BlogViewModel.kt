package com.example.levelup.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.repository.NoticiaRepository
import kotlinx.coroutines.launch


data class BlogPost(
    val id: String,
    val title: String,
    val author: String,
    val date: String,
    val excerpt: String,
    val externalUrl: String?,
    val imageUrl: String?
)

data class BlogUiState(
    val isLoading: Boolean = false,
    val posts: List<BlogPost> = emptyList(),
    val error: String? = null
)

class BlogViewModel : ViewModel() {

    private val repo = NoticiaRepository()

    val ui = mutableStateOf(BlogUiState(isLoading = true))

    fun load() {
        viewModelScope.launch {
            ui.value = BlogUiState(isLoading = true)

            try {
                val articles = repo.getGamingNews()

                val mapped = articles.mapIndexed { index, it ->
                    BlogPost(
                        id       = it.url ?: "id-$index",
                        title    = it.title ?: "Noticia Gamer",
                        author   = it.author ?: (it.source?.name ?: "Desconocido"),
                        date     = it.publishedAt ?: "",
                        excerpt  = it.description ?: it.content ?: "",
                        externalUrl = it.url,
                        imageUrl = it.urlToImage
                    )
                }

                ui.value = BlogUiState(
                    isLoading = false,
                    posts = mapped
                )

            } catch (e: Exception) {
                ui.value = BlogUiState(
                    isLoading = false,
                    error = e.message ?: "Error al cargar noticias"
                )
            }
        }
    }
}

