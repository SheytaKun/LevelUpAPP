package com.example.levelup.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    nav: NavHostController,
    vm: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDone: (String) -> Unit
) {
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current
    val state = vm.ui.value
    var show1 by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(false) }

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

                OutlinedTextField(
                    value = state.email,
                    onValueChange = vm::onEmail,
                    label = { Text("Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.pass,
                    onValueChange = vm::onPass,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (show1) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { TextButton(onClick = { show1 = !show1 }) { Text(if (show1) "Ocultar" else "Ver") } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.pass2,
                    onValueChange = vm::onPass2,
                    label = { Text("Repetir contraseña") },
                    singleLine = true,
                    visualTransformation = if (show2) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { TextButton(onClick = { show2 = !show2 }) { Text(if (show2) "Ocultar" else "Ver") } },
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.error != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        vm.submit { email ->
                            if (!isPreview) onDone(email)
                        }
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
