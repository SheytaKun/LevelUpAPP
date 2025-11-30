package com.example.levelup.data.repository

import com.example.levelup.data.dao.UsuarioDao
import com.example.levelup.data.model.UsuarioEntity

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun registrar(nombre: String, email: String, password: String): Result<Unit> {
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

    suspend fun login(email: String, password: String): Result<UsuarioEntity> {
        val usuario = usuarioDao.login(email, password)
        return if (usuario != null) {
            Result.success(usuario)
        } else {
            Result.failure(Exception("Credenciales incorrectas"))
        }
    }
}