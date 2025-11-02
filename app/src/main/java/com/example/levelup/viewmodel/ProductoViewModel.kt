package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito.asStateFlow()

    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            val listaActual = _carrito.value.toMutableList()
            val existente = listaActual.indexOfFirst { it.nombre == producto.nombre }

            if (existente >= 0) {
                val anterior = listaActual[existente]
                val nuevaCantidad = try {
                    anterior.stock.toInt() + producto.stock.toInt()
                } catch (_: Exception) {
                    producto.stock.coerceAtLeast(1)
                }

                listaActual[existente] = anterior.copy(stock = nuevaCantidad)
            } else {
                listaActual.add(producto)
            }
            _carrito.value = listaActual
        }
    }

    fun eliminarDelCarrito(nombre: String) {
        viewModelScope.launch {
            _carrito.value = _carrito.value.filterNot { it.nombre == nombre }
        }
    }

    fun limpiarCarrito() {
        viewModelScope.launch {
            _carrito.value = emptyList()
        }
    }
}
