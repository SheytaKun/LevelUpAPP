package com.example.levelup.ui.offers

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.levelup.data.repository.StaticOfferData
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.OffersViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    offersViewModel: OffersViewModel
) {
    // ✅ LISTA ESTÁTICA DE OFERTAS
    val productos = StaticOfferData.offers

    // ✅ STOCK DINÁMICO DESDE ROOM
    val stockMap by offersViewModel.stockMap.collectAsState()

    val descuentos = StaticOfferData.descuentos
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = { Text("Ofertas", color = OnSurface) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = OnSurface)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = OnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgBlack)
                .padding(12.dp)
        ) {
            if (productos.isEmpty()) {
                Text(
                    "No hay ofertas disponibles por el momento",
                    color = OnSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos) { p ->

                        val descuento = descuentos[p.codigo] ?: 0
                        val precioOriginal =
                            if (descuento > 0) StaticOfferData.precioOriginal(p.precio, descuento) else null

                        // 🔥 STOCK ACTUAL DESDE ROOM (si no está, usa el del estático)
                        val stockActual = stockMap[p.codigo] ?: p.stock

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("product/${Uri.encode(p.codigo)}")
                                },
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = p.imagenUrl,
                                    contentDescription = p.nombre,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    if (descuento > 0) {
                                        Box(
                                            modifier = Modifier
                                                .background(Color.Red, RoundedCornerShape(6.dp))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "-$descuento%",
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(6.dp))

                                    Text(
                                        p.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = OnSurface
                                    )

                                    if (precioOriginal != null && descuento > 0) {
                                        Text(
                                            text = moneda.format(precioOriginal),
                                            color = Color.Gray,
                                            textDecoration = TextDecoration.LineThrough,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            text = moneda.format(p.precio),
                                            color = SecondaryNeon,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    } else {
                                        Text(
                                            text = moneda.format(p.precio),
                                            color = PrimaryBlue,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // ✅ STOCK VIENDO ROOM
                                    Text(
                                        text = "Stock: $stockActual",
                                        color = SecondaryNeon,
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    Button(
                                        onClick = {
                                            cartViewModel.addToCartByCode(p.codigo)
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "${p.nombre} agregado al carrito",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PrimaryBlue,
                                            contentColor = OnSurface
                                        ),
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text("Agregar al Carrito")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
