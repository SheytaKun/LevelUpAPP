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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerMenu(
    username: String,
    navController: NavHostController,
    isDuoc: Boolean = false
) {
    val ctx = LocalContext.current

    LaunchedEffect(navController) {
        android.util.Log.d("NAV", "DrawerMenu  nav=${System.identityHashCode(navController)}")
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Header con íconos arriba a la derecha
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            // Íconos (perfil + carrito)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = { navController.navigate("cart") }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Títulos y chip abajo a la izquierda
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

        // Items del Drawer
        LazyColumn(modifier = Modifier.weight(1f)) {
            item { SectionLabel("Catálogo") }
            item { DrawerItem("Juegos de Mesa", Icons.Default.SportsEsports) {
                navController.navigate("catalogo?categoria=${Uri.encode("Juegos de Mesa")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Accesorios", Icons.Default.Headphones) {
                navController.navigate("catalogo?categoria=${Uri.encode("Accesorios")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Consolas", Icons.Default.VideogameAsset) {
                navController.navigate("catalogo?categoria=${Uri.encode("Consolas")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Computadores Gamers", Icons.Default.Computer) {
                navController.navigate("catalogo?categoria=${Uri.encode("Computadores Gamers")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Sillas Gamers", Icons.Default.Chair) {
                navController.navigate("catalogo?categoria=${Uri.encode("Sillas Gamers")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Mouse", Icons.Default.Mouse) {
                navController.navigate("catalogo?categoria=${Uri.encode("Mouse")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Mousepad", Icons.Default.ViewComfy) {
                navController.navigate("catalogo?categoria=${Uri.encode("Mousepad")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Poleras Personalizadas", Icons.Default.Checkroom) {
                navController.navigate("catalogo?categoria=${Uri.encode("Poleras Personalizadas")}") { launchSingleTop = true }
            } }
            item { DrawerItem("Polerones Gamers Personalizados", Icons.Default.Checkroom) {
                navController.navigate("catalogo?categoria=${Uri.encode("Polerones Gamers Personalizados")}") { launchSingleTop = true }
            } }

            item { SectionLabel("Comunidad") }
            item { DrawerItem("Blog & Noticias", Icons.Default.Article) {
                navController.navigate("blog")
            } }
            item { DrawerItem("Eventos (Mapa)", Icons.Default.Map) {
                navController.navigate("events")
            } }

            item {
                DrawerItem("Soporte WhatsApp", Icons.Default.SupportAgent) {
                    val text = "Hola, necesito soporte con mi pedido en Level-Up Gamer."
                    val url = "https://wa.me/56912345678?text=${Uri.encode(text)}"
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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