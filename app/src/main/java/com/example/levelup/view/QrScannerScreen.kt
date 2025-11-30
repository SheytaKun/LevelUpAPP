package com.example.levelup.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup.utils.QrScanner
import com.example.levelup.viewmodel.QrViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    viewModel: QrViewModel,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit,
    navController: NavController
) {
    val qrResult by viewModel.qrResult.observeAsState()
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear QR") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!hasCameraPermission) {
                Text(
                    "Permiso de cámara requerido",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = onRequestPermission) {
                    Text("Conceder permiso de cámara")
                }
            } else if (qrResult == null && isScanning) {
                Text(
                    "Escanea un código QR",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    QrScanner(
                        onQrCodeScanned = { qrContent ->
                            val previousEntry = navController.previousBackStackEntry
                            previousEntry?.savedStateHandle?.set("qr_result", qrContent)

                            viewModel.onQrDetected(qrContent)

                            Toast.makeText(context, "QR detectado: $qrContent", Toast.LENGTH_SHORT).show()

                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    Surface(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.Center),
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.primary
                        )
                    ) {}
                }
            } else if (qrResult != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "QR Detectado:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            qrResult!!.content,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.clearResult()
                            isScanning = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Escanear otro código QR")
                    }
                }
            }
        }
    }
}
