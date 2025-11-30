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

    IconButton(
        onClick = { expanded = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Usuario"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        if (usuario != null) {
            DropdownMenuItem(
                text = { Text(usuario.nombre) },
                onClick = { }
            )
            DropdownMenuItem(
                text = { Text(usuario.email) },
                onClick = { }
            )
            DropdownMenuItem(
                text = { Text("Ver mi cuenta") },
                onClick = {
                    expanded = false
                    navController.navigate("profile")
                }
            )
            DropdownMenuItem(
                text = { Text("Cerrar sesión") },
                onClick = {
                    SessionManager.usuarioActual = null
                    expanded = false
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        } else {
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
