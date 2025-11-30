package com.example.levelup.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.levelup.data.session.SessionManager

@Composable
fun UserMenuAction(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val usuario = SessionManager.usuarioActual

    if (usuario != null) {
        IconButton(
            onClick = { navController.navigate("profile") },
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Ir al perfil",
                tint = androidx.compose.ui.graphics.Color(0xFF39FF14) // SecondaryNeon color hint
            )
        }
    } else {
        IconButton(
            onClick = { expanded = true },
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Opciones de cuenta"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Iniciar sesión") },
                onClick = {
                    expanded = false
                    navController.navigate("login")
                }
            )
            DropdownMenuItem(
                text = { Text("Registrarse") },
                onClick = {
                    expanded = false
                    navController.navigate("register")
                }
            )
        }
    }
}
