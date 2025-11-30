package com.example.levelup.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

// 🎮 Colores LevelUp
private val PrimaryBlue   = Color(0xFF1E90FF)
private val NeonGreen     = Color(0xFF39FF14)
private val DarkBg        = Color(0xFF000000)
private val DarkSurface   = Color(0xFF18181C)
private val OnDark        = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(
    navController: NavHostController,
    emailFromSession: String?,
    vm: ProfileViewModel = viewModel()
) {
    val state = vm.uiState.value
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(emailFromSession) {
        if (!state.loaded) {
            emailFromSession?.let { vm.load(it) }
        }
    }

    // 🔹 Nombre limpio para mostrar (sin "Gamer Pro")
    val safeName = remember(state.name, emailFromSession) {
        when {
            state.name.isNotBlank() && state.name != "Gamer Pro" -> state.name
            !emailFromSession.isNullOrBlank() -> {
                val base = emailFromSession.substringBefore("@")
                base.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
            else -> ""
        }
    }

    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = OnDark,
        unfocusedTextColor = OnDark,
        disabledTextColor = OnDark.copy(alpha = 0.6f),

        focusedContainerColor = DarkSurface,
        unfocusedContainerColor = DarkSurface,
        disabledContainerColor = DarkSurface.copy(alpha = 0.5f),

        cursorColor = NeonGreen,

        focusedIndicatorColor = PrimaryBlue,
        unfocusedIndicatorColor = OnDark.copy(alpha = 0.5f),
        disabledIndicatorColor = OnDark.copy(alpha = 0.3f),

        focusedLabelColor = PrimaryBlue,
        unfocusedLabelColor = OnDark.copy(alpha = 0.7f),

        focusedLeadingIconColor = OnDark.copy(alpha = 0.9f),
        unfocusedLeadingIconColor = OnDark.copy(alpha = 0.6f),
        disabledLeadingIconColor = OnDark.copy(alpha = 0.3f),

        focusedPlaceholderColor = OnDark.copy(alpha = 0.5f),
        unfocusedPlaceholderColor = OnDark.copy(alpha = 0.5f)
    )

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Configuración de cuenta",
                        color = OnDark,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnDark
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DarkSurface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHost) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkBg)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Datos de tu cuenta",
                color = OnDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Actualiza tu nombre, correo y teléfono. Los cambios se guardan en tu cuenta LevelUp y se verán en tu perfil.",
                color = OnDark.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )

            // 🔳 Card de configuración
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Nombre completo (sin Gamer Pro)
                    OutlinedTextField(
                        value = safeName,
                        onValueChange = { vm.onNameChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre completo") },
                        placeholder = { Text("Ingresa tu nombre") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        colors = textFieldColors
                    )

                    // Correo electrónico (solo lectura)
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        enabled = false,
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        colors = textFieldColors
                    )

                    // Teléfono
                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = vm::onPhoneChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Teléfono") },
                        placeholder = { Text("Ej: +56 9 1234 5678") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        colors = textFieldColors
                    )

                    // Dirección
                    OutlinedTextField(
                        value = state.address,
                        onValueChange = vm::onAddressChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Dirección") },
                        placeholder = { Text("Ingresa tu dirección") },
                        colors = textFieldColors
                    )

                    // Beneficio DUOC UC
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Beneficio DUOC UC",
                                color = OnDark,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Marca si eres estudiante o docente DUOC UC (20% OFF)",
                                color = OnDark.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Switch(
                            checked = state.isDuoc,
                            onCheckedChange = vm::onToggleDuoc,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DarkBg,
                                checkedTrackColor = NeonGreen,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = OnDark.copy(alpha = 0.4f)
                            )
                        )
                    }

                    if (state.error != null) {
                        Text(
                            text = "⚠ ${state.error}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 💾 Botón Guardar cambios
            Button(
                onClick = {
                    vm.save {
                        scope.launch {
                            snackbarHost.showSnackbar("Cambios guardados en tu cuenta ✅")
                        }
                        navController.popBackStack() // volvemos al perfil
                    }
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (state.isLoading) "Guardando..." else "Guardar cambios",
                    fontWeight = FontWeight.Bold
                )
            }

            // ❌ Botón Cancelar
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancelar", fontWeight = FontWeight.Medium)
            }
        }

        // Overlay de carga
        if (state.isLoading && !state.loaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = NeonGreen)
            }
        }
    }
}
