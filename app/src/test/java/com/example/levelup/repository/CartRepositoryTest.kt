package com.example.levelup.repository

import com.example.levelup.data.dao.CartDao
import com.example.levelup.data.model.CartItem
import com.example.levelup.data.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CartRepositoryTest {

    private val cartDao: CartDao = mockk(relaxed = true)
    private val repository = CartRepository(cartDao)

    @Test
    fun `updateQuantity elimina item cuando cantidad es cero`() = runTest {
        // Arrange
        val item = CartItem(
            id = 1,
            productId = 30,
            quantity = 2
        )

        // Act
        repository.updateQuantity(cartItem = item, newQuantity = 0)

        // Assert
        coVerify(exactly = 1) { cartDao.deleteCartItem(item) }
        coVerify(exactly = 0) { cartDao.insertCartItem(any()) }
    }
}
