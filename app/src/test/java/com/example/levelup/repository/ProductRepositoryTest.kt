package com.example.levelup.repository

import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.Producto
import com.example.levelup.data.repository.ProductoRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductRepositoryTest {

    private val productDao: ProductoDao = mockk(relaxed = true)
    private val repository = ProductoRepository(productDao)

    @Test
    fun `obtenerProductos retorna el mismo flow entregado por el dao`() {
        // Arrange
        val listaProductos = listOf(
            Producto(
                id = 1,
                codigo = "P001",
                categoria = "Periféricos",
                nombre = "Mouse Gamer X",
                descripcion = "Mouse con RGB y 6 botones",
                precio = 15990,
                stock = 12,
                imagenUrl = "https://example.com/mouse.jpg"
            ),
            Producto(
                id = 2,
                codigo = "P002",
                categoria = "Teclados",
                nombre = "Teclado Mecánico RGB",
                descripcion = "Interruptores blue y retroiluminación",
                precio = 34990,
                stock = 5,
                imagenUrl = "https://example.com/teclado.jpg"
            )
        )

        val flowEsperado: Flow<List<Producto>> = flowOf(listaProductos)

        every { productDao.obtenerProductos() } returns flowEsperado

        // Act
        val resultado = repository.obtenerProductos()

        // Assert
        assertEquals(flowEsperado, resultado)
    }
}
