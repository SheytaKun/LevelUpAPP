package com.example.levelup.ui.product

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelup.data.ProductRepository
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    codigo: String
) {
    val product = ProductRepository.findByCode(codigo)
    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(product?.nombre ?: "Producto") }) }
    ) { inner ->
        Box(modifier = Modifier.padding(inner).fillMaxSize().padding(16.dp)) {
            if (product == null) {
                Text("Producto no encontrado")
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(product.nombre, style = MaterialTheme.typography.headlineSmall)
                    Text("Código: ${product.codigo}", style = MaterialTheme.typography.bodySmall)
                    Text("Categoría: ${product.categoria}", style = MaterialTheme.typography.bodyMedium)
                    Text("Stock: ${product.stock}", style = MaterialTheme.typography.bodyMedium)
                    Text("Precio: ${moneda.format(product.precio)}", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(product.descripcion, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            // navegar a la pantalla de formulario (se añadió en AppNav)
                            navController.navigate("producto_form/${Uri.encode(product.nombre)}/${product.precio}")
                        }) {
                            Text("Abrir formulario / añadir")
                        }
                        OutlinedButton(onClick = { navController.popBackStack() }) {
                            Text("Volver")
                        }
                    }
                }
            }
        }
    }
}