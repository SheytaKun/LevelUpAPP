package com.example.levelup.ui.home

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelup.view.DrawerMenu
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.example.levelup.R

// 🎨 Paleta gamer
private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

data class HeroProduct(val imageRes: Int, val title: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuestraDatosScreen(
    navController: NavHostController,
    username: String
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerMenu(username, navController) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Level-Up Gamer", fontWeight = FontWeight.Bold, color = OnSurface)
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = OnSurface)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = OnSurface)
                        }
                        IconButton(onClick = { navController.navigate("cart") }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = OnSurface)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
                )
            },

            //FOOTER FIJO
            bottomBar = {
                FooterSection()
            },

            containerColor = BgBlack
        ) { inner ->
            HomeScrollableContent(
                navController = navController,
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(BgBlack)
            )
        }
    }
}

@Composable
private fun HomeScrollableContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val products = listOf(
        HeroProduct(R.drawable.dia_mundial_del_lol, "Arma tu propio pc con Level Gamer"),
        HeroProduct(R.drawable.cultura_juvenil,      "T1 Tricampeon"),
        HeroProduct(R.drawable.pc_ultragamer,              "SETUP STREAMER")
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),   // SOLO EL CONTENIDO SCROLLEA
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "ENVÍOS A TODO CHILE",
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )

        HeroCarousel(products, Modifier.fillMaxWidth())

        Spacer(Modifier.height(20.dp))

        HighlightsSection(navController)

        Spacer(Modifier.height(100.dp)) // espacio antes del footer fijo
    }
}

@Composable
private fun FooterSection() {
    Surface(
        color = SurfaceDark,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(color = OnSurface.copy(alpha = 0.3f))

            Spacer(Modifier.height(8.dp))

            Text("Level-Up Gamer · Redes sociales",
                color = OnSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall)

            Text("© 2025 Todos los derechos reservados",
                color = OnSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun HighlightsSection(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigInfoCard(
                title = "Ofertas",
                subtitle = "Promos activas",
                accentColor = SecondaryNeon,
                imageRes = R.drawable.oferta_gamer,
                modifier = Modifier
                    .weight(1f)
                    .height(170.dp)
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Ofertas")}")
            }

            BigInfoCard(
                title = "Descuentos Especiales",
                subtitle = "Por tiempo limitado",
                accentColor = PrimaryBlue,
                imageRes = R.drawable.descuento,
                modifier = Modifier
                    .weight(1f)
                    .height(170.dp)
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Descuentos Especiales")}")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigInfoCard(
                title = "Top Compras",
                subtitle = "Lo más vendido",
                accentColor = Color(0xFFFFC107),
                imageRes = R.drawable.top,
                modifier = Modifier
                    .weight(1f)
                    .height(170.dp)
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Top Ventas")}")
            }

            BigInfoCard(
                title = "Nuevos Productos",
                subtitle = "Recién agregados",
                accentColor = Color(0xFF9C27B0),
                imageRes = R.drawable.nuevo,
                modifier = Modifier
                    .weight(1f)
                    .height(170.dp)
            ) {
                navController.navigate("catalogo?categoria=${Uri.encode("Nuevos Productos")}")
            }
        }
    }
}

@Composable
fun BigInfoCard(
    title: String,
    subtitle: String,
    accentColor: Color,
    imageRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(6.dp)
                            .background(accentColor)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = title,
                        color = accentColor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = subtitle,
                        color = OnSurface,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Ver más")
                }
            }
        }
    }
}

@Composable
fun HeroCarousel(
    products: List<HeroProduct>,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(products.size) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % products.size
        }
    }

    Card(
        modifier = modifier.heightIn(min = 280.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Crossfade(targetState = currentIndex, label = "hero-carousel") { index ->
                Image(
                    painter = painterResource(id = products[index].imageRes),
                    contentDescription = products[index].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                products.forEachIndexed { dotIndex, _ ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .background(
                                if (dotIndex == currentIndex)
                                    SecondaryNeon
                                else
                                    PrimaryBlue.copy(alpha = 0.4f)
                            )
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = products[currentIndex].title,
                color = OnSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }
    }
}
