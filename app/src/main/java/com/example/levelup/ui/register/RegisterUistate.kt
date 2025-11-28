package com.example.levelup.ui.register

data class RegisterUistate (
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)