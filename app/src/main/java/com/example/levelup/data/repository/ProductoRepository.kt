package com.example.levelup.data.repository

import com.example.levelup.dao.ProductoDao
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    // Insertar un producto
    suspend fun insertarProducto(producto: Producto) {
        productoDao.insertarProducto(producto)
    }

    // Insertar varios productos
    suspend fun insertarProductos(productos: List<Producto>) {
        productoDao.insertarProductos(productos)
    }

    // Obtener todos los productos
    fun obtenerProductos(): Flow<List<Producto>> {
        return productoDao.obtenerProductos()
    }

    // Obtener producto por ID
    suspend fun obtenerProductoPorId(id: Int): Producto? {
        return productoDao.obtenerProductoPorId(id)
    }

    // Obtener producto por código (JM001, AC002, etc.)
    suspend fun obtenerProductoPorCodigo(codigo: String): Producto? {
        return productoDao.obtenerProductoPorCodigo(codigo)
    }

    // Filtrar productos por categoría
    fun obtenerPorCategoria(categoria: String): Flow<List<Producto>> {
        return productoDao.obtenerPorCategoria(categoria)
    }

    // Buscar productos por texto (nombre o descripción)
    fun buscarProductos(query: String): Flow<List<Producto>> {
        return productoDao.buscarProductos(query)
    }

    // Eliminar un producto específico
    suspend fun eliminarProducto(producto: Producto) {
        productoDao.eliminarProducto(producto)
    }

    // Eliminar todos los productos (por ejemplo, para limpiar el catálogo local)
    suspend fun eliminarTodos() {
        productoDao.eliminarTodos()
    }

    // Actualizar stock de un producto
    suspend fun actualizarStock(id: Int, nuevoStock: Int) {
        productoDao.actualizarStock(id, nuevoStock)
    }
}