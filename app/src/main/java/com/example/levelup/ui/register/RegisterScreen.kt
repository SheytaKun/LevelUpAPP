package com.example.levelup.ui.register

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.ui.common.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    nav: NavHostController,
    onDone: (String) -> Unit
) {
    // ---------- ViewModel con factory (Room + Application) ----------
    val context = LocalContext.current
    val app = context.applicationContext as Application
    val factory = remember { AppViewModelFactory(app) }
    val vm: RegisterViewModel = viewModel(factory = factory)

    val state by vm.uiState.collectAsState()
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current

    // Ahora los campos los manejamos aquí, no dentro del ViewModel
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }
    var show1 by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(false) }

    // Cuando el registro sea exitoso en Room => llamar onDone(email)
    LaunchedEffect(state.success) {
        if (state.success) {
            if (!isPreview) {
                onDone(email)
            }
            vm.consumirSuccess()
        }
    }

    // ---------- Colores gamer que ya usabas ----------
    val dark = darkColorScheme(
        primary = Color(0xFF1E90FF),
        secondary = Color(0xFF39FF14),
        background = Color(0xFF000000),
        surface = Color(0xFF18181C),
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White
    )

    MaterialTheme(colorScheme = dark) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear cuenta") },
                    navigationIcon = {
                        IconButton(onClick = { nav.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))

                // 👉 Nombre (lo usamos para llenar UsuarioEntity.nombre)
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (show1) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { show1 = !show1 }) {
                            Text(if (show1) "Ocultar" else "Ver")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = pass2,
                    onValueChange = { pass2 = it },
                    label = { Text("Repetir contraseña") },
                    singleLine = true,
                    visualTransformation = if (show2) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { show2 = !show2 }) {
                            Text(if (show2) "Ocultar" else "Ver")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // ---------- Errores desde el ViewModel (Room, validación, etc.) ----------
                if (state.error != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                // ---------- Loading ----------
                if (state.isLoading) {
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        // Validación básica antes de llamar al ViewModel
                        if (nombre.isBlank() || email.isBlank() || pass.isBlank() || pass2.isBlank()) {
                            // Puedes mejorarlo mostrando error local si quieres
                            return@Button
                        }
                        if (pass != pass2) {
                            // Lo ideal sería setear un error local,
                            // pero por ahora se puede reutilizar el estado:
                            // (esto requeriría exponer un método en VM o manejarlo aquí)
                            // Para mantenerlo simple, dejamos que Room maneje lo demás.
                            return@Button
                        }

                        vm.registrar(nombre, email, pass)
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(if (state.isLoading) "Creando..." else "Registrarme")
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { nav.popBackStack() }) {
                    Text("¿Ya tienes cuenta? Inicia sesión")
                }
            }
        }
    }
}
