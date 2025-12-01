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
import com.example.levelup.data.repository.StaticProductData
import com.example.levelup.data.repository.StaticOfferData
import com.example.levelup.data.repository.StaticSpecialDiscountData
import com.example.levelup.data.repository.StaticNewProductsData   // 👈 NUEVO IMPORT
import com.example.levelup.viewmodel.CartViewModel
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
    codigo: String,
    cartViewModel: CartViewModel
) {
    // 1) BUSCAR EN CATÁLOGO NORMAL
    val fromCatalog = StaticProductData.findByCode(codigo)
    // 2) SI NO ESTÁ, BUSCAR EN OFERTAS
    val fromOffers  = StaticOfferData.offers.find { it.codigo == codigo }
    // 3) SI NO ESTÁ, BUSCAR EN DESCUENTOS ESPECIALES
    val fromSpecial = StaticSpecialDiscountData.products.find { it.codigo == codigo }
    // 4) SI NO ESTÁ, BUSCAR EN NUEVOS PRODUCTOS
    val fromNew     = StaticNewProductsData.newProducts.find { it.codigo == codigo }

    val product = fromCatalog ?: fromOffers ?: fromSpecial ?: fromNew
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    // ¿De dónde viene este producto?
    val esOferta = fromOffers != null
    val esDescuentoEspecial = fromSpecial != null
    val esNuevo = fromNew != null

    // Descuento y precio original según el origen
    val descuento: Int?
    val precioOriginal: Int?

    if (product != null) {
        when {
            esOferta -> {
                val d = StaticOfferData.descuentos[product.codigo]
                descuento = d
                precioOriginal = if (d != null && d > 0) {
                    StaticOfferData.precioOriginal(product.precio, d)
                } else null
            }
            esDescuentoEspecial -> {
                val d = StaticSpecialDiscountData.descuentos[product.codigo]
                descuento = d
                precioOriginal = if (d != null && d > 0) {
                    StaticSpecialDiscountData.precioOriginal(product.precio, d)
                } else null
            }
            else -> {
                // Nuevos productos y catálogo normal no tienen descuento
                descuento = null
                precioOriginal = null
            }
        }
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
            if (product == null) {
                Text(
                    "Producto no encontrado",
                    color = OnSurface
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // ===== TARJETA CON IMAGEN =====
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

                    // ===== INFORMACIÓN DEL PRODUCTO =====
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        // BADGE DE TIPO
                        if (esOferta || esDescuentoEspecial || esNuevo) {
                            val badgeText = when {
                                esDescuentoEspecial -> "DESCUENTO ESPECIAL"
                                esOferta -> "OFERTA"
                                esNuevo -> "NUEVO"
                                else -> ""
                            }
                            val badgeColor = when {
                                esDescuentoEspecial -> Color.Magenta
                                esOferta -> Color.Red
                                esNuevo -> Color(0xFF9C27B0) // morado para "NUEVO"
                                else -> SurfaceDark
                            }

                            Box(
                                modifier = Modifier
                                    .background(badgeColor, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = badgeText,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(4.dp))
                        }

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

                        // ==== PRECIO NORMAL VS DESCUENTO ====
                        if (precioOriginal != null && descuento != null && descuento > 0) {
                            Text(
                                text = "Antes: ${moneda.format(precioOriginal)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Text(
                                text = "Ahora: ${moneda.format(product.precio)}  (-$descuento%)",
                                style = MaterialTheme.typography.titleMedium,
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                "Precio: ${moneda.format(product.precio)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        product.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnSurface,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(Modifier.height(8.dp))

                    // ===== BOTÓN AGREGAR AL CARRITO =====
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

                    // ===== BOTONES INFERIORES =====
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