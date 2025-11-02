package com.example.levelup.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelup.navigation.Routes
import com.example.levelup.navigation.navToCategory
import com.example.levelup.navigation.navToDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuestraDatosScreen(
    username: String,
    navController: NavHostController   // 👈 aquí el cambio
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Level-Up Gamer") },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.Cart) }) {
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

            ElevatedButton(
                onClick = { navToDrawer(navController, username) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Menu, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Abrir menú (Drawer)")
            }

            ElevatedButton(
                onClick = { navToCategory(navController, null) },  // ✅ ahora compila
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver catálogo")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(Routes.Profile) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Perfil")
                }
                OutlinedButton(
                    onClick = { navController.navigate(Routes.Blog) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Article, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Blog")
                }
            }

            OutlinedButton(
                onClick = { navController.navigate(Routes.Events) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Eventos (Mapa)")
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
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
