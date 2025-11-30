package com.example.levelup.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.database.ProductoDataBase
import com.example.levelup.data.repository.UsuarioRepository
import com.example.levelup.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: UsuarioRepository

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        val db = ProductoDataBase.getDataBase(app)
        val dao = db.usuarioDao()
        repo = UsuarioRepository(dao)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            val result = repo.login(email, password)

            _uiState.value = result.fold(
                onSuccess = { user ->
                    SessionManager.usuarioActual = user

                    LoginUiState(
                        isLoading = false,
                        usuarioLogueado = user
                    )
                },
                onFailure = { e ->
                    LoginUiState(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }
}
