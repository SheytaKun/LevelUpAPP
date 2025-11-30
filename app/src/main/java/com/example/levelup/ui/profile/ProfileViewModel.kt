package com.example.levelup.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.Profile
import com.example.levelup.data.repository.IProfileRepository
import com.example.levelup.data.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: IProfileRepository = ProfileRepository()
) : ViewModel() {

    var uiState = androidx.compose.runtime.mutableStateOf(ProfileUiState())
        private set

    fun load(email: String) {
        if (uiState.value.loaded) return
        viewModelScope.launch {
            uiState.value = uiState.value.copy(isLoading = true, error = null)
            runCatching { repo.getProfile(email) }
                .onSuccess { p ->
                    uiState.value = uiState.value.copy(
                        name = p.name,
                        email = p.email,
                        phone = p.phone,
                        address = p.address,
                        isDuoc = p.isDuoc,
                        isLoading = false,
                        loaded = true
                    )
                }
                .onFailure { e ->
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error cargando perfil"
                    )
                }
        }
    }

    fun onNameChange(v: String)    { uiState.value = uiState.value.copy(name = v) }
    fun onPhoneChange(v: String)   { uiState.value = uiState.value.copy(phone = v) }
    fun onAddressChange(v: String) { uiState.value = uiState.value.copy(address = v) }
    fun onToggleDuoc(v: Boolean)   { uiState.value = uiState.value.copy(isDuoc = v) }

    fun save(onDone: () -> Unit) { // ✅ ya no es @Composable
        val s = uiState.value
        viewModelScope.launch {
            uiState.value = s.copy(isLoading = true, error = null)
            val p = Profile(s.name, s.email, s.phone, s.address, s.isDuoc)
            runCatching { repo.updateProfile(p) }
                .onSuccess {
                    uiState.value = s.copy(isLoading = false)
                    onDone()
                }
                .onFailure { e ->
                    uiState.value = s.copy(
                        isLoading = false,
                        error = e.message ?: "No se pudo guardar"
                    )
                }
        }
    }
}
