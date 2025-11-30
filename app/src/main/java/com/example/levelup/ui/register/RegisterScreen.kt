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
import androidx.compose.material.icons.filled.QrCodeScanner
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
import androidx.compose.runtime.livedata.observeAsState
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
    val context = LocalContext.current
    val app = context.applicationContext as Application
    val factory = remember { AppViewModelFactory(app) }
    val vm: RegisterViewModel = viewModel(factory = factory)

    val state by vm.uiState.collectAsState()
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }
    var show1 by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(false) }
    val qrResult = nav.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("qr_result")?.observeAsState()

    LaunchedEffect(qrResult?.value) {
        qrResult?.value?.let {
            email = it
            nav.currentBackStackEntry?.savedStateHandle?.remove<String>("qr_result")
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            if (!isPreview) {
                onDone(email)
            }
            vm.consumirSuccess()
        }
    }

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
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { nav.navigate("qrScanner") }) {
                             Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear QR")
                        }
                    }
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

                if (state.error != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                if (state.isLoading) {
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (nombre.isBlank() || email.isBlank() || pass.isBlank() || pass2.isBlank()) {
                            return@Button
                        }
                        if (pass != pass2) {
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
