package com.example.levelup

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.levelup.viewmodel.QrViewModel
import com.example.levelup.utils.CameraPermissionHelper
import com.example.levelup.data.repository.StaticProductData
import com.example.levelup.data.database.ProductoDataBase
import com.example.levelup.data.repository.CartRepository
import com.example.levelup.data.repository.ProductoRepository
import com.example.levelup.navigation.AppNav
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.CartViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val qrViewModel: QrViewModel by viewModels()

    private val cartViewModel: CartViewModel by viewModels {
        val db = ProductoDataBase.getDataBase(applicationContext)
        CartViewModelFactory(
            CartRepository(db.cartDao()),
            ProductoRepository(db.productoDao())
        )
    }

    private var hasCameraPermission by mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (granted) {
            Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = ProductoDataBase.getDataBase(applicationContext)
        lifecycleScope.launch {
            try {
                db.productoDao().insertarProductos(StaticProductData.products)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)

        setContent {
            val nav: NavHostController = rememberNavController()

            LaunchedEffect(nav) {
                android.util.Log.d("NAV", "MainActivity nav=${System.identityHashCode(nav)}")
            }

            MaterialTheme {
                Surface {
                    AppNav(
                        navController = nav,
                        qrViewModel = qrViewModel,
                        cartViewModel = cartViewModel,
                        hasCameraPermission = hasCameraPermission,
                        onRequestPermission = { requestPermissionLauncher.launch(Manifest.permission.CAMERA) }
                    )
                }
            }
        }

        qrViewModel.qrResult.observe(this) { result ->
            result?.let {
                Toast.makeText(this, "QR detectado: ${it.content}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)
    }
}
