package com.example.levelup.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelup.R

@Composable
fun DrawerMenu(
    username: String,
    navController: NavHostController,
    isDuoc: Boolean = false
) {
    val ctx = LocalContext.current

    val primary = Color(0xFF1E90FF)
    val secondary = Color(0xFF39FF14)
    val background = Color(0xFF000000)
    val surface = Color(0xFF18181C)
    val onPrimary = Color.White
    val onSurface = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {

        // 🔵 TOP BAR Gamer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(surface)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            // Botón menú
            IconButton(
                onClick = { /* abrir drawer */ },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = onSurface)
            }

            // Nombre gamer
            Text(
                text = "LEVEL-UP GAMER",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                color = secondary
            )

            // Carrito + perfil
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = { navController.navigate("cart") }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = onSurface)
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = onSurface)
                }
            }
        }

        // Subtítulo
        Text(
            text = "ENVÍOS A TODO CHILE",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            color = primary,
            fontWeight = FontWeight.Bold
        )

        // 🟢 HERO CARD versión gamer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {

                // Placeholder de imagen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF222228)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dia_mundial_del_lol),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Text(
                    text = "CONJUNTOS A LA MODA",
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                // "Puntos" gamer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    listOf(primary, secondary, primary).forEach { color ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {

            item { SectionLabel("Catálogo", onSurface) }

            item { DrawerItem("Juegos de Mesa", Icons.Default.SportsEsports, onSurface) {
                navController.navigate("catalogo?categoria=${Uri.encode("Juegos de Mesa")}")
            } }

            item { DrawerItem("Accesorios", Icons.Default.Headphones, onSurface) {
                navController.navigate("catalogo?categoria=${Uri.encode("Accesorios")}")
            } }

            item { DrawerItem("Consolas", Icons.Default.VideogameAsset, onSurface) {
                navController.navigate("catalogo?categoria=${Uri.encode("Consolas")}")
            } }

            item { DrawerItem("Computadores Gamers", Icons.Default.Computer, onSurface) {
                navController.navigate("catalogo?categoria=${Uri.encode("Computadores Gamers")}")
            } }

        }

        //  Footer gamer
        Text(
            text = "© 2025 Level-Up Gamer",
            color = onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: ImageVector,
    tintColor: Color,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label, color = tintColor) },
        selected = false,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label, tint = tintColor) },
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
fun SectionLabel(text: String, color: Color) {
    Text(
        text = text,
        color = color.copy(alpha = 0.7f),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
