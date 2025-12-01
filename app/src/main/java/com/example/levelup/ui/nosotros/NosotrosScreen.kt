package com.example.levelup.ui.nosotros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(
    navController: NavHostController
) {
    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nosotros",
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = OnSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceDark
                )
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(BgBlack)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // === TÍTULO PRINCIPAL ===
            Text(
                text = "LevelUp Store",
                style = MaterialTheme.typography.headlineMedium,
                color = SecondaryNeon,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tu tienda gamer de confianza para subir de nivel tu setup, tu experiencia y tus partidas.",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurface,
                textAlign = TextAlign.Center
            )

            // === QUIÉNES SOMOS ===
            InfoCard(
                titulo = "¿Quiénes somos?",
                contenido = "Somos una tienda enfocada en el mundo gamer, dedicada a ofrecer productos de calidad como periféricos, accesorios, sillas y artículos para tu setup. Nuestro objetivo es que puedas encontrar todo en un solo lugar, con una experiencia clara y sencilla."
            )

            // === MISIÓN ===
            InfoCard(
                titulo = "Nuestra misión",
                contenido = "Acompañarte en cada partida, entrega y compra, ofreciendo productos confiables, precios competitivos y una experiencia pensada para jugadores casuales y también competitivos."
            )

            // === VISIÓN ===
            InfoCard(
                titulo = "Nuestra visión",
                contenido = "Convertirnos en una referencia dentro del ambiente gamer, potenciando la experiencia de juego con tecnología, comodidad y estilo, siempre escuchando el feedback de nuestra comunidad."
            )

            // === VALORES ===
            InfoCard(
                titulo = "Nuestros valores",
                contenido = "• Compromiso con el cliente\n" +
                        "• Transparencia en las compras\n" +
                        "• Pasión por los videojuegos\n" +
                        "• Innovación constante en productos y servicios"
            )

            // === CONTACTO ===
            InfoCard(
                titulo = "Contáctanos",
                contenido = "Si tienes dudas, sugerencias o necesitas ayuda con un producto, puedes escribirnos a:\n\n" +
                        "📧 contacto@levelupstore.cl\n" +
                        "📱 +56 9 1234 5678\n" +
                        "⏰ Horario de atención: Lun a Vie, 10:00 a 19:00 hrs."
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para ir al catálogo
            Button(
                onClick = { navController.navigate("catalogo?categoria=${"Todas"}") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = OnSurface
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text("Ir al catálogo")
            }
        }
    }
}

@Composable
private fun InfoCard(
    titulo: String,
    contenido: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = SecondaryNeon,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurface
            )
        }
    }
}
