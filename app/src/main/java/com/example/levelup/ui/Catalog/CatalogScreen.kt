package com.example.levelup.ui.catalog

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelup.data.repository.StaticProductData
import com.example.levelup.viewmodel.CartViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController,
    categoria: String?,
    cartViewModel: CartViewModel
) {
    val productos = StaticProductData.byCategory(categoria)
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Catálogo: ${categoria ?: "Todas"}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Ir al carrito")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("cart") }
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Ir al carrito")
            }
        }
    ) { inner ->
        Box(modifier = Modifier.padding(inner).fillMaxSize().padding(12.dp)) {
            if (productos.isEmpty()) {
                Text("No hay productos para mostrar", modifier = Modifier.padding(8.dp))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(productos) { p ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("product/${Uri.encode(p.codigo)}")
                                }
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(p.categoria, style = MaterialTheme.typography.bodySmall)
                                Spacer(Modifier.height(6.dp))
                                Text(moneda.format(p.precio), style = MaterialTheme.typography.bodyLarge)
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
                                    modifier = Modifier.fillMaxWidth()
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
