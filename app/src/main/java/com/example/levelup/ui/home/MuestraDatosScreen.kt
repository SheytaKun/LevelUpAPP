package com.example.levelup.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuestraDatosScreen(
    navController: NavHostController,
    username: String
) {
    var moreOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Level-Up Gamer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { moreOpen = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                        }
                        DropdownMenu(
                            expanded = moreOpen,
                            onDismissRequest = { moreOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Perfil") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                onClick = {
                                    moreOpen = false
                                    navController.navigate("profile")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Carrito") },
                                leadingIcon = { Icon(Icons.Default.ShoppingCart, null) },
                                onClick = {
                                    moreOpen = false
                                    navController.navigate("cart")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Blog & Noticias") },
                                leadingIcon = { Icon(Icons.Default.Article, null) },
                                onClick = {
                                    moreOpen = false
                                    navController.navigate("blog")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Eventos (Mapa)") },
                                leadingIcon = { Icon(Icons.Default.Map, null) },
                                onClick = {
                                    moreOpen = false
                                    navController.navigate("events")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Catálogo (todas)") },
                                leadingIcon = { Icon(Icons.Default.Storefront, null) },
                                onClick = {
                                    moreOpen = false
                                    navController.navigate("catalogo?categoria=") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Zona central vacía para tu futuro hero/landing
        }
    }
}
