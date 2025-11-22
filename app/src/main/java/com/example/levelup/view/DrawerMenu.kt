package com.example.levelup.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnPrimary     = Color.White
private val OnSecondary   = Color.Black
private val OnSurface     = Color.White

@Composable
fun DrawerMenu(
    username: String,
    navController: NavHostController,
    isDuoc: Boolean = false
) {
    val ctx = LocalContext.current

    // 👉 Estados para abrir/cerrar secciones
    var catalogExpanded by remember { mutableStateOf(false) }
    var moreExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(SurfaceDark)
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Íconos superiores
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopRoundIcon(Icons.Default.Accessibility, "Accesibilidad")
            TopRoundIcon(Icons.Default.Search, "Buscar")
            TopRoundIcon(Icons.Default.VolumeUp, "Audio")
        }

        Spacer(Modifier.height(16.dp))

        // Botón de contacto (WhatsApp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SecondaryNeon)
                .clickable {
                    val text = "Hola, necesito soporte con mi pedido en Level-Up Gamer."
                    val url = "https://wa.me/56938942576?text=${Uri.encode(text)}"
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                .padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "Contactar",
                tint = OnSecondary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Contáctanos",
                color = OnSecondary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        SectionHeader(
            title = "Catálogo",
            expanded = catalogExpanded,
            onClick = { catalogExpanded = !catalogExpanded }
        )

        if (catalogExpanded) {
            DrawerItemGamer(
                label = "Novedades",
                icon = Icons.Default.Star
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Novedades")}") {
                    launchSingleTop = true
                }
            }

            DrawerItemGamer(
                label = "Vestidos",
                icon = Icons.Default.Checkroom
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Vestidos")}") {
                    launchSingleTop = true
                }
            }

            DrawerItemGamer(
                label = "Poleras",
                icon = Icons.Default.Checkroom
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Poleras")}") {
                    launchSingleTop = true
                }
            }

            DrawerItemGamer(
                label = "Pantalones",
                icon = Icons.Default.Dashboard
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Pantalones")}") {
                    launchSingleTop = true
                }
            }

            DrawerItemGamer(
                label = "Polerones",
                icon = Icons.Default.Hiking
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Polerones")}") {
                    launchSingleTop = true
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        SectionHeader(
            title = "Más",
            expanded = moreExpanded,
            onClick = { moreExpanded = !moreExpanded }
        )

        if (moreExpanded) {
            DrawerItemGamer(
                label = "Blog & Noticias",
                icon = Icons.Default.Article
            ) {
                navController.navigate("blog")
            }

            DrawerItemGamer(
                label = "Eventos",
                icon = Icons.Default.Map
            ) {
                navController.navigate("events")
            }
        }
    }
}

@Composable
private fun TopRoundIcon(
    icon: ImageVector,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(PrimaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = OnPrimary
        )
    }
}

@Composable
private fun DrawerItemGamer(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = SecondaryNeon,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            color = OnSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = if (expanded) "Contraer" else "Expandir",
            tint = PrimaryBlue
        )
    }
}
