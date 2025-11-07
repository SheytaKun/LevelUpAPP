package com.example.levelup.ui.register

data class RegisterUistate (
    val email: String = "",
    val pass: String = "",
    val pass2: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)