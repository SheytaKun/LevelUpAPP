package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.data.repository.CartRepository
import com.example.levelup.data.repository.ProductoRepository

class CartViewModelFactory(
    private val cartRepository: CartRepository,
    private val productoRepository: ProductoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository, productoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
