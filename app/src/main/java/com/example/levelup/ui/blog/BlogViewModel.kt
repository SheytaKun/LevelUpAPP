package com.example.levelup.ui.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.repository.blog.BlogRepository
import com.example.levelup.data.repository.blog.FakeBlogRepository
import kotlinx.coroutines.launch

class BlogViewModel(
    private val repo: BlogRepository = FakeBlogRepository()
) : ViewModel() {

    var ui = androidx.compose.runtime.mutableStateOf(BlogUiState())
        private set

    fun load() {
        if (ui.value.posts.isNotEmpty() || ui.value.isLoading) return
        viewModelScope.launch {
            ui.value = ui.value.copy(isLoading = true, error = null)
            runCatching { repo.getPosts() }
                .onSuccess { list ->
                    ui.value = ui.value.copy(isLoading = false, posts = list)
                }
                .onFailure { e ->
                    ui.value = ui.value.copy(isLoading = false, error = e.message ?: "Error cargando blog")
                }
        }
    }
}
