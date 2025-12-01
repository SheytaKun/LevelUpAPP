package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.Producto
import com.example.levelup.data.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    // Todos los productos en un StateFlow para usar en Compose
    val productos: StateFlow<List<Producto>> = repository.obtenerProductos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun productosPorCategoria(categoria: String?): Flow<List<Producto>> {
        return if (categoria.isNullOrBlank()) {
            repository.obtenerProductos()
        } else {
            repository.obtenerPorCategoria(categoria)
        }
    }

    suspend fun getProductoPorCodigo(codigo: String): Producto? {
        return repository.obtenerProductoPorCodigo(codigo)
    }
}
