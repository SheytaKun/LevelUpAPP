package com.example.levelup.data.repository

import com.example.levelup.data.dao.CartDao
import com.example.levelup.data.model.CartItem
import com.example.levelup.data.model.CartWithProduct
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val cartItems: Flow<List<CartWithProduct>> = cartDao.getCartItems()

    suspend fun addToCart(productId: Int, quantity: Int = 1) {
        val existingItem = cartDao.getCartItemByProductId(productId)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            cartDao.insertCartItem(updatedItem)
        } else {
            val newItem = CartItem(productId = productId, quantity = quantity)
            cartDao.insertCartItem(newItem)
        }
    }

    suspend fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity > 0) {
            cartDao.insertCartItem(cartItem.copy(quantity = newQuantity))
        } else {
            cartDao.deleteCartItem(cartItem)
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
