package com.example.levelup.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    emailFromSession: String?,
    vm: ProfileViewModel = viewModel()
) {
    val state = vm.uiState.value
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Cargar perfil una sola vez
    LaunchedEffect(emailFromSession) {
        emailFromSession?.let { vm.load(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Perfil de Usuario") })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading && !state.loaded) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = state.name,
                    onValueChange = vm::onNameChange,
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = {},
                    label = { Text("Correo electrónico") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.phone,
                    onValueChange = vm::onPhoneChange,
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.address,
                    onValueChange = vm::onAddressChange,
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = state.isDuoc,
                        onCheckedChange = vm::onToggleDuoc
                    )
                    Text("Soy estudiante/profesor DUOC UC (20% OFF)")
                }

                if (state.error != null) {
                    Text(
                        text = "⚠️ ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🔵 Guardar cambios
                Button(
                    onClick = {
                        vm.save {
                            scope.launch {
                                snackbarHost.showSnackbar("Perfil guardado ✅")
                            }
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (state.isLoading) "Guardando..." else "Guardar cambios")
                }

                // 🔙 Volver sin guardar
                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver sin guardar")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true } // limpia toda la pila
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}
