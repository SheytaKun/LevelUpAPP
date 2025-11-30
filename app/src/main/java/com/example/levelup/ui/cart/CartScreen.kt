package com.example.levelup.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup.data.model.CartWithProduct
import com.example.levelup.viewmodel.CartViewModel

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    var couponCode by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Carrito de Compras", 
                        color = OnSurface, 
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceDark
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Tu carrito está vacío",
                            color = OnSurface,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("catalogo?categoria=") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Ir al Catálogo")
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceDark
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Resumen del Pedido",
                            style = MaterialTheme.typography.titleMedium,
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(cartItems) { item ->
                                CartItemRow(
                                    cartItem = item,
                                    onIncrease = { viewModel.updateQuantity(item.cartItem, item.cartItem.quantity + 1) },
                                    onDecrease = { viewModel.updateQuantity(item.cartItem, item.cartItem.quantity - 1) }
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = couponCode,
                                onValueChange = { couponCode = it },
                                label = { Text("Código de cupón") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SecondaryNeon,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = SecondaryNeon,
                                    unfocusedLabelColor = Color.Gray,
                                    focusedTextColor = OnSurface,
                                    unfocusedTextColor = OnSurface,
                                    cursorColor = SecondaryNeon
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { /* Lógica de aplicar cupón */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SecondaryNeon,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Aplicar")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total:",
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnSurface
                            )
                            Text(
                                text = "$${String.format("%.0f", totalPrice)}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryNeon
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.clearCart() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFFFF4444)
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFF4444))
                                )
                            ) {
                                Text("Limpiar Carrito")
                            }

                            Button(
                                onClick = { /* Implementar checkout */ },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlue,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Comprar",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartWithProduct,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF333333),
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Precio Unitario: $${cartItem.product.precio}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Subtotal: $${cartItem.product.precio * cartItem.cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryNeon,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = onDecrease) {
                    Icon(
                        Icons.Default.Remove, 
                        contentDescription = "Disminuir",
                        tint = Color.White
                    )
                }
                
                Text(
                    text = cartItem.cartItem.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                IconButton(onClick = onIncrease) {
                    Icon(
                        Icons.Default.Add, 
                        contentDescription = "Aumentar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
