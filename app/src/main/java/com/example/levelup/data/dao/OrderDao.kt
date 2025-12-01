package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.levelup.data.model.OrderEntity
import com.example.levelup.data.model.OrderItemEntity
import com.example.levelup.data.model.OrderWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Transaction
    suspend fun createOrder(order: OrderEntity, items: List<OrderItemEntity>) {
        val orderId = insertOrder(order)
        val itemsWithId = items.map { it.copy(orderId = orderId) }
        insertOrderItems(itemsWithId)
    }

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: Long): OrderEntity?

    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY date DESC")
    fun getOrdersByUserId(userId: Int): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY date DESC")
    fun getOrdersWithItemsByUserId(userId: Int): Flow<List<OrderWithItems>>
}
