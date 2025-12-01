package com.example.levelup.data.repository

import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(
    private val productoDao: ProductoDao
) {

    suspend fun disminuirStock(codigo: String, cantidad: Int) {
        productoDao.disminuirStock(codigo, cantidad)
    }

    suspend fun insertarProducto(producto: Producto) {
        productoDao.insertarProducto(producto)
    }

    suspend fun insertarProductos(productos: List<Producto>) {
        productoDao.insertarProductos(productos)
    }

    fun obtenerProductos(): Flow<List<Producto>> {
        return productoDao.obtenerProductos()
    }

    suspend fun obtenerProductoPorCodigo(codigo: String): Producto? {
        return productoDao.obtenerProductoPorCodigo(codigo)
    }

    fun obtenerPorCategoria(categoria: String): Flow<List<Producto>> {
        return productoDao.obtenerPorCategoria(categoria)
    }

    fun buscarProductos(query: String): Flow<List<Producto>> {
        return productoDao.buscarProductos(query)
    }

    suspend fun eliminarProducto(producto: Producto) {
        productoDao.eliminarProducto(producto)
    }

    suspend fun eliminarTodos() {
        productoDao.eliminarTodos()
    }

    suspend fun actualizarStock(id: Int, nuevoStock: Int) {
        productoDao.actualizarStock(id, nuevoStock)
    }

    fun obtenerOfertas(): Flow<List<Producto>> =
        productoDao.obtenerPorCategoria("Ofertas")
}
