package com.example.levelup.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.NumberFormat
import java.util.Locale

data class CarritoItem(
    val nombre: String,
    val precioUnitario: Int,
    val cantidad: Int,
    val notas: String = ""
) {
    val subtotal: Int get() = precioUnitario * cantidad
}

class CarritoViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<CarritoItem>>(emptyList())
    val items: StateFlow<List<CarritoItem>> = _items

    fun agregar(item: CarritoItem) {
        // Si el mismo producto existe, acumula cantidad
        val actualizada = _items.value.toMutableList()
        val idx = actualizada.indexOfFirst { it.nombre == item.nombre && it.notas == item.notas }
        if (idx >= 0) {
            val existente = actualizada[idx]
            actualizada[idx] = existente.copy(cantidad = existente.cantidad + item.cantidad)
        } else {
            actualizada.add(item)
        }
        _items.value = actualizada
    }

    fun limpiar() { _items.value = emptyList() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    vm: CarritoViewModel = viewModel()
) {
    val precioInt = remember(precio) {
        precio.filter { it.isDigit() }.toIntOrNull() ?: 0
    }
    var cantidad by remember { mutableStateOf(1) }
    var notas by remember { mutableStateOf(TextFieldValue("")) }

    val moneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    val total = precioInt * cantidad

    val items by vm.items.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Total: ${moneda.format(total)}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Precio: ${moneda.format(precioInt)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text(text = "Cantidad", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1f))
                OutlinedButton(
                    onClick = { if (cantidad > 1) cantidad-- },
                    enabled = cantidad > 1
                ) { Text("−") }

                Text(
                    text = cantidad.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                OutlinedButton(onClick = { cantidad++ }) { Text("+") }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas / Personalización (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (precioInt > 0 && cantidad > 0) {
                        vm.agregar(
                            CarritoItem(
                                nombre = nombre,
                                precioUnitario = precioInt,
                                cantidad = cantidad,
                                notas = notas.text.trim()
                            )
                        )
                    }
                },
                enabled = precioInt > 0 && cantidad > 0,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text("Agregar al carrito")
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Carrito",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            if (items.isEmpty()) {
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 8.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    items(items) { it ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(it.nombre, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "x${it.cantidad} • ${moneda.format(it.precioUnitario)} c/u",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (it.notas.isNotBlank()) {
                                    Text(
                                        "Notas: ${it.notas}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(
                                    "Subtotal: ${moneda.format(it.subtotal)}",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Mouse Gamer Logitech G502 HERO",
        precio = "49990"
    )
}