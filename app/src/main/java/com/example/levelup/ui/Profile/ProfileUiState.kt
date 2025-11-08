package com.example.levelup.ui.profile

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val isDuoc: Boolean = false,
    val isLoading: Boolean = false,
    val loaded: Boolean = false,
    val error: String? = null
)
