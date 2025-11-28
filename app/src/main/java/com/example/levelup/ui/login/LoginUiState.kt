package com.example.levelup.ui.login

import com.example.levelup.data.model.UsuarioEntity

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val usuarioLogueado: UsuarioEntity? = null
)
