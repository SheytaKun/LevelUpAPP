package com.example.levelup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val userId: Int? = null,
    val date: Long,
    val totalAmount: Double,
    val status: String,
    
    val fullName: String,
    val address: String,
    val commune: String,
    val city: String,
    val region: String,
    val postalCode: String,
    val contactEmail: String,
    
    val transactionCode: String
)
