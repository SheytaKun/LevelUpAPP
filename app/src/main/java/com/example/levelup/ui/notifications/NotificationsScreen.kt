package com.example.levelup.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.data.database.ProductoDataBase
import com.example.levelup.data.model.OrderItemEntity
import com.example.levelup.data.model.OrderWithItems
import com.example.levelup.data.repository.OrderRepository
import com.example.levelup.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private val PrimaryBlue = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack = Color(0xFF000000)
private val SurfaceDark = Color(0xFF18181C)
private val OnSurface = Color.White

class NotificationsViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    // We will use OrderWithItems directly
}

class NotificationsViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val database = remember { ProductoDataBase.getDataBase(context) }
    val orderRepository = remember {
        OrderRepository(database.orderDao(), database.productoDao())
    }
    
    val user = SessionManager.usuarioActual
    val ordersState = remember { mutableStateOf<List<OrderWithItems>>(emptyList()) }
    
    LaunchedEffect(user) {
        if (user != null) {
             orderRepository.getOrdersWithItemsByUserId(user.id).collect {
                 ordersState.value = it
             }
        }
    }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = { Text("Mis Compras", color = OnSurface, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = OnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgBlack)
        ) {
            if (ordersState.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes compras registradas.", color = OnSurface)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ordersState.value) { orderWithItems ->
                        ExpandableOrderCard(orderWithItems)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableOrderCard(orderWithItems: OrderWithItems) {
    var expanded by remember { mutableStateOf(false) }
    val order = orderWithItems.order
    val items = orderWithItems.items
    
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Orden #${order.transactionCode}",
                    style = MaterialTheme.typography.titleMedium,
                    color = SecondaryNeon,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Colapsar" else "Expandir",
                    tint = Color.Gray
                )
            }
            
            Text(
                text = dateFormat.format(Date(order.date)),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${currencyFormat.format(order.totalAmount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = order.status,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (order.status == "COMPLETED") SecondaryNeon else PrimaryBlue
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color.Gray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Detalles de productos:", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.quantity}x ${item.productName}",
                            color = OnSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = currencyFormat.format(item.unitPrice * item.quantity),
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.Gray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Dirección: ${order.address}, ${order.commune}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
