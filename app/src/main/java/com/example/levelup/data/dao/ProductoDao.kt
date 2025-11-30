package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(productos: List<Producto>)

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun obtenerProductos(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    @Query("SELECT * FROM productos WHERE codigo = :codigo LIMIT 1")
    suspend fun obtenerProductoPorCodigo(codigo: String): Producto?

    @Query("SELECT * FROM productos WHERE categoria = :categoria ORDER BY nombre ASC")
    fun obtenerPorCategoria(categoria: String): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%'")
    fun buscarProductos(query: String): Flow<List<Producto>>

    @Delete
    suspend fun eliminarProducto(producto: Producto)

    @Query("DELETE FROM productos")
    suspend fun eliminarTodos()

    @Query("UPDATE productos SET stock = :nuevoStock WHERE id = :id")
    suspend fun actualizarStock(id: Int, nuevoStock: Int)
}