package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.CartWithProduct
import com.example.levelup.data.repository.CartRepository
import com.example.levelup.data.repository.OrderRepository
import com.example.levelup.data.repository.OrderItemDto
import com.example.levelup.data.repository.ShippingInfo
import com.example.levelup.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> = _orderState

    fun createOrder(
        shippingInfo: ShippingInfo,
        cartItems: List<CartWithProduct>
    ) {
        if (cartItems.isEmpty()) return

        viewModelScope.launch {
            _orderState.value = OrderState.Loading
            try {
                val userId = SessionManager.usuarioActual?.id
                
                val orderItems = cartItems.map { 
                    OrderItemDto(
                        productId = it.product.id,
                        productName = it.product.nombre,
                        unitPrice = it.product.precio,
                        quantity = it.cartItem.quantity
                    )
                }

                val transactionCode = orderRepository.createOrder(
                    userId = userId,
                    shippingInfo = shippingInfo,
                    items = orderItems
                )

                cartRepository.clearCart()

                _orderState.value = OrderState.Success(transactionCode)
            } catch (e: Exception) {
                e.printStackTrace()
                _orderState.value = OrderState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun resetOrderState() {
        _orderState.value = OrderState.Idle
    }
}

sealed class OrderState {
    object Idle : OrderState()
    object Loading : OrderState()
    data class Success(val transactionCode: String) : OrderState()
    data class Error(val message: String) : OrderState()
}
