package com.example.levelup.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

data class HeroProduct(
    val imageRes: Int,
    val title: String
)

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
        drawerContent = {
            DrawerMenu(username = username, navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Level-Up Gamer", fontWeight = FontWeight.Bold, color = OnSurface)
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = OnSurface)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(Icons.Default.Person, contentDescription = "Perfil", tint = OnSurface)
                        }
                        IconButton(onClick = { navController.navigate("cart") }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = OnSurface)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
                )
            },
            containerColor = BgBlack
        ) { inner ->
            HomeContent(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(BgBlack)
            )
        }
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier) {

    val products = listOf(
        HeroProduct(R.drawable.dia_mundial_del_lol, "CONJUNTOS A LA MODA"),
        HeroProduct(R.drawable.cultura_juvenil, "SET GAMER RGB"),
        HeroProduct(R.drawable.negrito, "SETUP STREAMER")
    )

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "ENVÍOS A TODO CHILE",
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            )

            HeroCarousel(
                products = products,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(1.dp)
                    .background(OnSurface.copy(alpha = 0.3f))
            )

            Spacer(Modifier.height(12.dp))

            Text("© 2025 Level-Up Gamer", color = OnSurface.copy(alpha = 0.7f))
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Crossfade(
                targetState = currentIndex,
                label = "hero-carousel"
            ) { index ->

                Image(
                    painter = painterResource(id = products[index].imageRes),
                    contentDescription = products[index].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(6.dp))


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

            Spacer(modifier = Modifier.height(6.dp))

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