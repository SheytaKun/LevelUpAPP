package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.CartItem
import com.example.levelup.data.model.CartWithProduct
import com.example.levelup.data.repository.CartRepository
import com.example.levelup.data.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productoRepository: ProductoRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartWithProduct>> = cartRepository.cartItems
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalPrice: StateFlow<Double> = cartRepository.cartItems.map { items ->
        items.sumOf { it.product.precio.toDouble() * it.cartItem.quantity }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun addToCartByCode(codigo: String, quantity: Int = 1) {
        viewModelScope.launch {
            val producto = productoRepository.obtenerProductoPorCodigo(codigo)
            if (producto != null) {
                cartRepository.addToCart(producto.id, quantity)
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(cartItem, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    fun confirmarCompra() {
        viewModelScope.launch {
            // 1) Snapshot actual del carrito
            val items = cartItems.value

            items.forEach { item ->
                productoRepository.disminuirStock(
                    codigo = item.product.codigo,
                    cantidad = item.cartItem.quantity
                )
            }

            cartRepository.clearCart()
        }
    }
}