package com.example.levelup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")

data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String,
    val categoria: String,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
)