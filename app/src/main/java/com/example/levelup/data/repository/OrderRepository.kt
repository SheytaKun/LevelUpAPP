package com.example.levelup.data.repository

import com.example.levelup.data.dao.OrderDao
import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.OrderEntity
import com.example.levelup.data.model.OrderItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class OrderRepository(
    private val orderDao: OrderDao,
    private val productoDao: ProductoDao
) {

    suspend fun createOrder(
        userId: Int?,
        shippingInfo: ShippingInfo,
        items: List<OrderItemDto>
    ): String {
        return withContext(Dispatchers.IO) {
            val totalAmount = items.sumOf { it.unitPrice.toDouble() * it.quantity }
            val transactionCode = UUID.randomUUID().toString().substring(0, 8).uppercase()

            val order = OrderEntity(
                userId = userId,
                date = System.currentTimeMillis(),
                totalAmount = totalAmount,
                status = "COMPLETED",
                fullName = shippingInfo.fullName,
                address = shippingInfo.address,
                commune = shippingInfo.commune,
                city = shippingInfo.city,
                region = shippingInfo.region,
                postalCode = shippingInfo.postalCode,
                contactEmail = shippingInfo.contactEmail,
                deliveryInstructions = shippingInfo.deliveryInstructions,
                transactionCode = transactionCode
            )

            val orderItems = items.map { 
                OrderItemEntity(
                    orderId = 0,
                    productId = it.productId,
                    productName = it.productName,
                    unitPrice = it.unitPrice,
                    quantity = it.quantity
                ) 
            }

            orderDao.createOrder(order, orderItems)

            items.forEach { item ->
                val currentProduct = productoDao.obtenerProductoPorId(item.productId)
                if (currentProduct != null) {
                    val newStock = currentProduct.stock - item.quantity
                    if (newStock >= 0) {
                        productoDao.actualizarStock(item.productId, newStock)
                    } else {
                        productoDao.actualizarStock(item.productId, 0)
                    }
                }
            }
            
            transactionCode
        }
    }
}

data class ShippingInfo(
    val fullName: String,
    val address: String,
    val commune: String,
    val city: String,
    val region: String,
    val postalCode: String,
    val contactEmail: String,
    val deliveryInstructions: String
)

data class OrderItemDto(
    val productId: Int,
    val productName: String,
    val unitPrice: Int,
    val quantity: Int
)
