package com.example.levelup.data.repository

import com.example.levelup.data.dao.UsuarioDao
import com.example.levelup.data.model.UsuarioEntity

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    // Registrar usuario (solo si no existe el email)
    suspend fun registrar(nombre: String, email: String, password: String): Result<Unit> {
        // ¿Ya existe ese correo?
        val existente = usuarioDao.obtenerPorEmail(email)
        if (existente != null) {
            return Result.failure(Exception("El correo ya está registrado"))
        }

        val usuario = UsuarioEntity(
            nombre = nombre,
            email = email,
            password = password
        )

        usuarioDao.insertarUsuario(usuario)
        return Result.success(Unit)
    }

    // Login
    suspend fun login(email: String, password: String): Result<UsuarioEntity> {
        val usuario = usuarioDao.login(email, password)
        return if (usuario != null) {
            Result.success(usuario)
        } else {
            Result.failure(Exception("Credenciales incorrectas"))
        }
    }
}