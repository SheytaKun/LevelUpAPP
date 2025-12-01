package com.example.levelup.ui.catalog

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.levelup.data.model.Producto
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.graphics.Color

// === MISMOS COLORES DEL HOME ===
private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController,
    categoria: String?,
    productoViewModel: ProductoViewModel,
    cartViewModel: CartViewModel
) {
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ✅ Traer productos DESDE ROOM según categoría
    val productosFlow = productoViewModel.productosPorCategoria(categoria)
    val productos by productosFlow.collectAsState(initial = emptyList())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Catálogo: ${categoria ?: "Todas"}",
                        color = OnSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Ir al carrito",
                            tint = OnSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceDark
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("cart") },
                containerColor = SecondaryNeon,
                contentColor = BgBlack
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Ir al carrito")
            }
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(BgBlack)
                .padding(12.dp)
        ) {

            if (productos.isEmpty()) {
                Text(
                    "No hay productos para mostrar",
                    color = OnSurface,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(productos) { p: Producto ->

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

                                // === IMAGEN DESDE ROOM ===
                                AsyncImage(
                                    model = p.imagenUrl,
                                    contentDescription = p.nombre,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(
                                        p.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = OnSurface
                                    )

                                    Text(
                                        p.categoria,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OnSurface.copy(alpha = 0.8f)
                                    )

                                    Text(
                                        moneda.format(p.precio),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = PrimaryBlue
                                    )

                                    Text(
                                        "Stock: ${p.stock}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SecondaryNeon
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
