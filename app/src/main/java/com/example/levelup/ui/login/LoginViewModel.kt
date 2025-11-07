package com.example.levelup.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState = androidx.compose.runtime.mutableStateOf(LoginUiState())
        private set
    private fun isDuocMail(email: String) =
        //email.endsWith("@duoc.cl", true) || email.endsWith("@profesor.duoc.cl", true)
        email.endsWith("admin@duoc.cl", true)

    private fun isEmailValid(email: String) =
        Regex("^[\\w.+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email)

    fun onUsernameChange(v: String) {
        uiState.value = uiState.value.copy(username = v, error = null) }
    fun onPasswordChange(v: String) {
        uiState.value = uiState.value.copy(password = v, error = null) }

    fun submit(onSuccess: (email: String, isDuoc: Boolean) -> Unit) {
        val email = uiState.value.username.trim().lowercase()
        val pass  = uiState.value.password

        when {
            email.isEmpty() || pass.isEmpty() -> {
                uiState.value = uiState.value.copy(error = "Completa correo y contraseña"); return
            }
            !isEmailValid(email) -> {
                uiState.value = uiState.value.copy(error = "Correo inválido"); return
            }
            pass.length < 6 -> {
                uiState.value = uiState.value.copy(error = "La contraseña debe tener al menos 6 caracteres"); return
            }
        }

        uiState.value = uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val ok = repo.login(email, pass)
                if (ok) {
                    onSuccess(email, isDuocMail(email))
                    uiState.value = LoginUiState()
                } else {
                    uiState.value = uiState.value.copy(error = "Credenciales inválidas")
                }
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message ?: "Error al iniciar sesión")
            } finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }
}
