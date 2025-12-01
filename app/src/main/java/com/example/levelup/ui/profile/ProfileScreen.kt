package com.example.levelup.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.data.session.SessionManager
import kotlinx.coroutines.launch

private val PrimaryBlue   = Color(0xFF1E90FF)
private val NeonGreen     = Color(0xFF39FF14)
private val DarkBg        = Color(0xFF000000)
private val CardBg        = Color(0xFFF7F7F7)

@Composable
fun ProfileScreen(
    navController: NavHostController,
    emailFromSession: String?,
    vm: ProfileViewModel = viewModel()
) {
    val ui = vm.uiState.value
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 🔹 Nombre que se mostrará SIEMPRE en el perfil (sin "Gamer Pro")
    val displayName = remember(ui.name, emailFromSession) {
        when {
            ui.name.isNotBlank() && ui.name != "Gamer Pro" -> ui.name
            !emailFromSession.isNullOrBlank() -> {
                val base = emailFromSession.substringBefore("@")
                base.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
            else -> "Usuario"
        }
    }

    LaunchedEffect(emailFromSession) {
        emailFromSession?.let { vm.load(it) }
    }

    Scaffold(
        containerColor = DarkBg,
        snackbarHost = { SnackbarHost(snackbarHost) }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(DarkBg)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🔵 HEADER PRO: degradado + curva inferior
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        )
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    PrimaryBlue,
                                    PrimaryBlue.copy(alpha = 0.9f),
                                    NeonGreen.copy(alpha = 0.6f)
                                )
                            )
                        )
                ) {
                    // Barra superior inline
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }

                        Text(
                            text = "Perfil",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = { navController.navigate("notifications") }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notificaciones",
                                tint = Color.White
                            )
                        }
                    }

                    // Avatar + info centrada
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = displayName, // 👈 AQUÍ USAMOS EL NOMBRE LIMPIO
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = ui.email,
                            color = Color.White.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(Modifier.height(6.dp))

                        // Badge según DUOC
                        if (ui.isDuoc) {
                            Surface(
                                color = NeonGreen,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "DUOC UC • 20% OFF",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                    )
                                )
                            }
                        } else {
                            Text(
                                text = "Usuario LevelUp Store",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // 🔘 Tarjetas de opciones
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileOption(
                        icon = Icons.Default.Settings,
                        title = "Configuración de cuenta",
                        onClick = { navController.navigate("account_settings") }
                    )
                    ProfileOption(
                        icon = Icons.Default.Info,
                        title = "Información personal",
                        onClick = { /* Navegar a otra pantalla si quieres */ }
                    )
                    ProfileOption(
                        icon = Icons.Default.Notifications,
                        title = "Notificaciones",
                        onClick = { navController.navigate("notifications") }
                    )
                    ProfileOption(
                        icon = Icons.Default.Wifi,
                        title = "Descarga solo por WiFi",
                        onClick = { /* TODO */ }
                    )
                    ProfileOption(
                        icon = Icons.Default.Fingerprint,
                        title = "Seguridad biométrica",
                        onClick = { /* TODO */ }
                    )
                }

                Spacer(Modifier.weight(1f))

                // 🔵 Botón principal
                Button(
                    onClick = {
                        vm.save {
                            scope.launch {
                                snackbarHost.showSnackbar("Perfil guardado ✔")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        "Guardar cambios",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(10.dp))

                // 🔴 Cerrar sesión
                OutlinedButton(
                    onClick = {
                        SessionManager.usuarioActual = null
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text(
                        "Cerrar sesión",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(20.dp))
            }

            // Overlay de carga inicial
            if (ui.isLoading && !ui.loaded) {
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
}

@Composable
fun ProfileOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF555555),
                modifier = Modifier.size(22.dp)
            )

            Spacer(Modifier.width(14.dp))

            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
