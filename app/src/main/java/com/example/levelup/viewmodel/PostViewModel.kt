package com.example.levelup.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.levelup.data.model.Post
import com.example.levelup.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {


    private val repository = PostRepository()

// Flujo mutable que contenga la lista de post

    private  val _postList = MutableStateFlow<List<Post>> (emptyList())

// flujo publico

    val postList: StateFlow<List<Post>> =_postList

    // Se llama automaticamente al inicio

    init{
        fetchPosts()
    }



// Manejo del flujo en segundo plano

    private fun fetchPosts(){
        viewModelScope.launch {
            try{
                _postList.value =repository.getPosts()
            } catch(e:Exception){
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }// fin launch

    }   // fin fun



}// fin class