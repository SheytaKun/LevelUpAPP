package com.example.levelup.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var ui = mutableStateOf(RegisterUistate())
        private set

    private fun isEmailValid(email: String) =
        Regex("^[\\w.+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email)

    fun onEmail(v: String)  { ui.value = ui.value.copy(email = v,  error = null) }
    fun onPass(v: String)   { ui.value = ui.value.copy(pass = v,   error = null) }
    fun onPass2(v: String)  { ui.value = ui.value.copy(pass2 = v,  error = null) }

    fun submit(onSuccess: (email: String) -> Unit) {
        val email = ui.value.email.trim().lowercase()
        val p1 = ui.value.pass
        val p2 = ui.value.pass2

        if (email.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
            ui.value = ui.value.copy(error = "Completa todos los campos")
            return
        }
        if (!isEmailValid(email)) {
            ui.value = ui.value.copy(error = "Correo inválido")
            return
        }
        if (p1.length < 6) {
            ui.value = ui.value.copy(error = "La contraseña debe tener al menos 6 caracteres")
            return
        }
        if (p1 != p2) {
            ui.value = ui.value.copy(error = "Las contraseñas no coinciden")
            return
        }


        ui.value = ui.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val ok = repo.register(email, p1)
                if (ok) {
                    onSuccess(email)
                    ui.value = RegisterUistate()
                } else {
                    ui.value = ui.value.copy(error = "No se pudo registrar")
                }
            } catch (e: Exception) {
                ui.value = ui.value.copy(error = e.message ?: "Error al registrar")
            } finally {
                ui.value = ui.value.copy(isLoading = false)
            }
        }
    }
}
