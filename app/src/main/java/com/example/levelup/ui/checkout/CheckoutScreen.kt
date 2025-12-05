package com.example.levelup.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.data.database.ProductoDataBase
import com.example.levelup.data.repository.CartRepository
import com.example.levelup.data.repository.OrderRepository
import com.example.levelup.data.repository.RegionRepository
import com.example.levelup.data.repository.ShippingInfo
import com.example.levelup.data.session.SessionManager
import com.example.levelup.viewmodel.*

private val PrimaryBlue = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack = Color(0xFF000000)
private val SurfaceDark = Color(0xFF18181C)
private val OnSurface = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val database = remember { ProductoDataBase.getDataBase(context) }
    val orderRepository = remember {
        OrderRepository(database.orderDao(), database.productoDao())
    }
    val cartRepository = remember {
        CartRepository(database.cartDao())
    }
    val regionRepository = remember {
        RegionRepository(database.regionDao())
    }

    val orderViewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(orderRepository, cartRepository)
    )

    val regionViewModel: RegionViewModel = viewModel(
        factory = RegionViewModelFactory(regionRepository)
    )

    val orderState by orderViewModel.orderState.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val regionsList by regionViewModel.regions.collectAsState()

    // Form state initialized empty
    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var commune by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }

    // Effect to populate fields if user is logged in
    LaunchedEffect(Unit) {
        val user = SessionManager.usuarioActual
        if (user != null) {
            fullName = user.nombre
            contactEmail = user.email
        }
    }

    var regionExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(orderState) {
        if (orderState is OrderState.Success) {
        }
    }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text("Checkout", color = OnSurface, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = OnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        }
    ) { paddingValues ->

        if (orderState is OrderState.Success) {
            val code = (orderState as OrderState.Success).transactionCode
            SuccessScreen(
                transactionCode = code,
                onHomeClick = {
                    orderViewModel.resetOrderState()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "Información de Envío",
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CheckoutTextField(value = fullName, onValueChange = { fullName = it }, label = "Nombre Completo")
                CheckoutTextField(value = address, onValueChange = { address = it }, label = "Dirección")

                ExposedDropdownMenuBox(
                    expanded = regionExpanded,
                    onExpandedChange = { regionExpanded = !regionExpanded },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    OutlinedTextField(
                        value = region,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Región") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SecondaryNeon,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = OnSurface,
                            unfocusedTextColor = OnSurface
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = regionExpanded,
                        onDismissRequest = { regionExpanded = false }
                    ) {
                        regionsList.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.name) },
                                onClick = {
                                    region = selectionOption.name
                                    regionExpanded = false
                                }
                            )
                        }
                    }
                }

                CheckoutTextField(value = city, onValueChange = { city = it }, label = "Ciudad")
                CheckoutTextField(value = commune, onValueChange = { commune = it }, label = "Comuna")
                CheckoutTextField(value = postalCode, onValueChange = { postalCode = it }, label = "Código Postal", keyboardType = KeyboardType.Number)
                CheckoutTextField(value = contactEmail, onValueChange = { contactEmail = it }, label = "Correo Electrónico", keyboardType = KeyboardType.Email)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (validateForm(fullName, address, region, city, commune, postalCode, contactEmail)) {
                            val shippingInfo = ShippingInfo(
                                fullName, address, commune, city, region, postalCode, contactEmail
                            )
                            orderViewModel.createOrder(shippingInfo, cartItems)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = orderState !is OrderState.Loading
                ) {
                    if (orderState is OrderState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Confirmar Compra", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
                
                if (orderState is OrderState.Error) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = (orderState as OrderState.Error).message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun CheckoutTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
}

fun validateForm(
    fullName: String,
    address: String,
    region: String,
    city: String,
    commune: String,
    postalCode: String,
    email: String
): Boolean {
    return fullName.isNotBlank() && address.isNotBlank() && region.isNotBlank() &&
            city.isNotBlank() && commune.isNotBlank() && postalCode.isNotBlank() && email.isNotBlank()
}

@Composable
fun SuccessScreen(transactionCode: String, onHomeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Compra Exitosa!",
            style = MaterialTheme.typography.headlineMedium,
            color = SecondaryNeon,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tu pedido ha sido registrado correctamente.",
            color = OnSurface,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Código de Transacción", color = Color.Gray)
                Text(transactionCode, style = MaterialTheme.typography.displaySmall, color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onHomeClick,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text("Volver al Inicio")
        }
    }
}
