package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.Producto
import com.example.levelup.data.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class OffersViewModel(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    // Mapa de stock: codigo -> stock actual en ROOM
    val stockMap: StateFlow<Map<String, Int>> =
        productoRepository.obtenerProductos()
            .map { productos ->
                productos.associate { it.codigo to it.stock }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())
}

class OffersViewModelFactory(
    private val productoRepository: ProductoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OffersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OffersViewModel(productoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
