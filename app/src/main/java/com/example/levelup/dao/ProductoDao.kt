package com.example.levelup.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    //Insertar un solo producto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto)

    //Insertar lista (para precargar catálogo completo)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(productos: List<Producto>)

    //Obtener todos los productos
    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun obtenerProductos(): Flow<List<Producto>>

    //Obtener producto por ID
    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    //Obtener producto por código (JM001, AC002, etc.)
    @Query("SELECT * FROM productos WHERE codigo = :codigo LIMIT 1")
    suspend fun obtenerProductoPorCodigo(codigo: String): Producto?

    //Filtrar por categoría
    @Query("SELECT * FROM productos WHERE categoria = :categoria ORDER BY nombre ASC")
    fun obtenerPorCategoria(categoria: String): Flow<List<Producto>>

    //Buscar por texto (nombre o descripción)
    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%'")
    fun buscarProductos(query: String): Flow<List<Producto>>

    //Eliminar un producto
    @Delete
    suspend fun eliminarProducto(producto: Producto)

    //Eliminar todos (por ejemplo, limpiar catálogo local antes de sincronizar)
    @Query("DELETE FROM productos")
    suspend fun eliminarTodos()

    //Actualizar stock
    @Query("UPDATE productos SET stock = :nuevoStock WHERE id = :id")
    suspend fun actualizarStock(id: Int, nuevoStock: Int)
}