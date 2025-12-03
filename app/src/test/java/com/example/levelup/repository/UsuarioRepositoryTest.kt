package com.example.levelup.repository

import com.example.levelup.data.dao.UsuarioDao
import com.example.levelup.data.model.UsuarioEntity
import com.example.levelup.data.repository.UsuarioRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UsuarioRepositoryTest {

    private val usuarioDao: UsuarioDao = mockk(relaxed = true)
    private val repository = UsuarioRepository(usuarioDao)

    @Test
    fun `registrar usuario falla si el correo ya existe y no inserta`() = runTest {
        // Arrange
        val email = "test@duoc.cl"

        val existente = UsuarioEntity(
            id = 1,
            nombre = "Usuario Existente",
            email = email,
            password = "123456"
        )

        coEvery { usuarioDao.obtenerPorEmail(email) } returns existente

        // Act
        val resultado = repository.registrar(
            nombre = "Nuevo Usuario",
            email = email,
            password = "abcdef"
        )

        // Assert
        assertTrue(resultado.isFailure)
        coVerify(exactly = 0) { usuarioDao.insertarUsuario(any()) }
    }
}
