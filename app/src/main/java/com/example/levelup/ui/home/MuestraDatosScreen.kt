package com.example.levelup.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuestraDatosScreen(
    username: String,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Level-Up Gamer") },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "¡Bienvenido, $username!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Explora el catálogo, revisa tu carrito o entra al menú para navegar por categorías.",
                style = MaterialTheme.typography.bodyMedium
            )

            // Abrir Drawer (tu pantalla tipo menú)
            ElevatedButton(
                onClick = {
                    navController.navigate("drawer/${Uri.encode(username)}") {
                        // si quieres quitar login del back stack:
                        // popUpTo("login") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Menu, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Abrir menú (Drawer)")
            }

            // Ir al catálogo (todas las categorías)
            ElevatedButton(
                onClick = {
                    navController.navigate("catalogo?categoria=") { launchSingleTop = true }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver catálogo")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("profile") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Perfil")
                }
                OutlinedButton(
                    onClick = { navController.navigate("blog") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Article, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Blog")
                }
            }

            OutlinedButton(
                onClick = { navController.navigate("events") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Eventos (Mapa)")
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
