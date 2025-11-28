package com.example.levelup.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.database.ProductoDataBase
import com.example.levelup.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: UsuarioRepository

    private val _uiState = MutableStateFlow(RegisterUistate())
    val uiState: StateFlow<RegisterUistate> = _uiState

    init {
        val db = ProductoDataBase.getDataBase(app)
        val dao = db.usuarioDao()
        repo = UsuarioRepository(dao)
    }

    fun registrar(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUistate(isLoading = true)

            val result = repo.registrar(nombre, email, password)

            _uiState.value = result.fold(
                onSuccess = {
                    RegisterUistate(
                        isLoading = false,
                        success = true
                    )
                },
                onFailure = { e ->
                    RegisterUistate(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun consumirSuccess() {
        _uiState.value = _uiState.value.copy(success = false)
    }
}
