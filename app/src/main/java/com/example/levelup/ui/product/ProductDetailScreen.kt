package com.example.levelup.ui.product

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
import com.example.levelup.data.model.Producto
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.ProductoViewModel
import java.text.NumberFormat
import java.util.Locale

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    codigo: String,  // El código del producto viene de la navegación
    cartViewModel: CartViewModel,
    productoViewModel: ProductoViewModel   // Recibe ProductoViewModel
) {
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    // Obtiene el producto desde ROOM o desde los datos estáticos usando el código
    val productoState = produceState<Producto?>(initialValue = null, key1 = codigo) {
        value = productoViewModel.getProductoPorCodigo(codigo)
    }
    val product = productoState.value

    // Descuento y precio según origen
    val descuento: Int?
    val precioOriginal: Int?

    if (product != null) {
        descuento = null
        precioOriginal = null
    } else {
        descuento = null
        precioOriginal = null
    }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        product?.nombre ?: "Producto",
                        color = OnSurface,
                        fontWeight = FontWeight.Bold
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
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(BgBlack)
                .padding(16.dp)
        ) {
            when {
                product == null -> {
                    Text(
                        "Cargando producto...",
                        color = OnSurface
                    )
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // TARJETA CON IMAGEN DESDE ROOM
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            AsyncImage(
                                model = product.imagenUrl,
                                contentDescription = product.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // INFORMACIÓN DEL PRODUCTO
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                product.nombre,
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnSurface
                            )
                            Text(
                                "Código: ${product.codigo}",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurface.copy(alpha = 0.8f)
                            )
                            Text(
                                "Categoría: ${product.categoria}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface
                            )
                            Text(
                                "Stock: ${product.stock}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SecondaryNeon
                            )

                            // PRECIO NORMAL VS DESCUENTO
                            Text(
                                "Precio: ${moneda.format(product.precio)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            product.descripcion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurface,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Spacer(Modifier.height(8.dp))

                        // BOTÓN AGREGAR AL CARRITO
                        Button(
                            onClick = {
                                cartViewModel.addToCartByCode(product.codigo)
                                navController.navigate("cart")
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SecondaryNeon,
                                contentColor = BgBlack
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Agregar al carrito")
                        }

                        // BOTONES INFERIORES
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(
                                        "producto_form/${Uri.encode(product.nombre)}/${product.precio}"
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlue,
                                    contentColor = OnSurface
                                ),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Editar")
                            }

                            OutlinedButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = SecondaryNeon
                                ),
                                shape = RoundedCornerShape(50),
                                border = BorderStroke(2.dp, SecondaryNeon)
                            ) {
                                Text("Volver")
                            }
                        }
                    }
                }
            }
        }
    }
}

