package com.example.levelup.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController,
    isDuoc: Boolean = false
) {
    val ctx = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = "Level-Up Gamer",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hola, $username",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )
                if (isDuoc) {
                    AssistChip(
                        onClick = { /* no-op */ },
                        label = { Text("20% OFF DUOC") },
                        leadingIcon = { Icon(Icons.Default.Verified, contentDescription = null) },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = Color.Black,
                            containerColor = Color(0xFF39FF14)
                        )
                    )
                }
            }
        }

        // Items
        LazyColumn(modifier = Modifier.weight(1f)) {

            // --- Catálogo (categorías del enunciado) ---
            item {
                SectionLabel("Catálogo")
            }
            item {
                DrawerItem(
                    label = "Juegos de Mesa",
                    icon = Icons.Default.SportsEsports
                ) {
                    navToCategory(navController, "Juegos de Mesa")
                }
            }
            item {
                DrawerItem("Accesorios", Icons.Default.Headphones) {
                    navToCategory(navController, "Accesorios")
                }
            }
            item {
                DrawerItem("Consolas", Icons.Default.VideogameAsset) {
                    navToCategory(navController, "Consolas")
                }
            }
            item {
                DrawerItem("Computadores Gamers", Icons.Default.Computer) {
                    navToCategory(navController, "Computadores Gamers")
                }
            }
            item {
                DrawerItem("Sillas Gamers", Icons.Default.Chair) {
                    navToCategory(navController, "Sillas Gamers")
                }
            }
            item {
                DrawerItem("Mouse", Icons.Default.Mouse) {
                    navToCategory(navController, "Mouse")
                }
            }
            item {
                DrawerItem("Mousepad", Icons.Default.ViewComfy) {
                    navToCategory(navController, "Mousepad")
                }
            }
            item {
                DrawerItem("Poleras Personalizadas", Icons.Default.Checkroom) {
                    navToCategory(navController, "Poleras Personalizadas")
                }
            }
            item {
                DrawerItem("Polerones Gamers Personalizados", Icons.Default.Checkroom) {
                    navToCategory(navController, "Polerones Gamers Personalizados")
                }
            }

            // --- Comunidad / Contenido ---
            item {
                SectionLabel("Comunidad")
            }
            item {
                DrawerItem("Blog & Noticias", Icons.Default.Article) {
                    navController.navigate("blog")
                }
            }
            item {
                DrawerItem("Eventos (Mapa)", Icons.Default.Map) {
                    navController.navigate("events")
                }
            }

            // --- Cuenta / Soporte ---
            item {
                SectionLabel("Cuenta")
            }
            item {
                DrawerItem("Carrito", Icons.Default.ShoppingCart) {
                    navController.navigate("cart")
                }
            }
            item {
                DrawerItem("Perfil", Icons.Default.Person) {
                    navController.navigate("profile")
                }
            }
            item {
                DrawerItem("Soporte WhatsApp", Icons.Default.SupportAgent) {
                    val text = "Hola, necesito soporte con mi pedido en Level-Up Gamer."
                    val url = "https://wa.me/56912345678?text=" +
                            URLEncoder.encode(text, StandardCharsets.UTF_8.toString())
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    ctx.startActivity(intent)
                }
            }
            item {
                DrawerItem("Compartir tienda", Icons.Default.Share) {
                    val send = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Mira Level-Up Gamer: ofertas y puntos LevelUp 💚💙")
                    }
                    ctx.startActivity(Intent.createChooser(send, "Compartir con…"))
                }
            }
        }

        // Footer
        Text(
            text = "© 2025 Level-Up Gamer",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

// --- Helpers UI ---

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
}

@Composable
private fun DrawerItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = false,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

private fun navToCategory(navController: NavController, categoria: String) {
    val encoded = URLEncoder.encode(categoria, StandardCharsets.UTF_8.toString())
    // Define tu destino: por ejemplo "catalogo?categoria={categoria}"
    navController.navigate("catalogo?categoria=$encoded") {
        launchSingleTop = true
    }
}