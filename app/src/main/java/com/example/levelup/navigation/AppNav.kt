package com.example.levelup.navigation

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.levelup.data.session.SessionManager
import com.example.levelup.ui.blog.BlogScreen
import com.example.levelup.ui.catalog.CatalogScreen
import com.example.levelup.ui.cart.CartScreen
import com.example.levelup.ui.home.MuestraDatosScreen
import com.example.levelup.ui.login.LoginScreen
import com.example.levelup.ui.product.ProductDetailScreen
import com.example.levelup.ui.profile.ProfileScreen
import com.example.levelup.ui.profile.AccountSettingsScreen
import com.example.levelup.ui.register.RegisterScreen
import com.example.levelup.view.ProductoFormScreen
import com.example.levelup.view.QrScannerScreen
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.QrViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    qrViewModel: QrViewModel,
    cartViewModel: CartViewModel,
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    LaunchedEffect(navController) {
        android.util.Log.d("NAV", "AppNav nav=${System.identityHashCode(navController)}")
    }

    val login           = "login"
    val home            = "home"
    val drawer          = "drawer/{username}"
    val catalogo        = "catalogo?categoria={categoria}"
    val product         = "product/{id}"
    val cart            = "cart"
    val profile         = "profile"
    val accountSettings = "account_settings"   // 👈 NUEVA RUTA
    val blog            = "blog"
    val events          = "events"
    val register        = "register"
    val productForm     = "producto_form/{nombre}/{precio}"
    val forgot          = "forgot"
    val qrScanner       = "qrScanner"

    NavHost(navController = navController, startDestination = home) {

        // 🏠 HOME
        composable(home) {
            val usuario = SessionManager.usuarioActual
            val username = usuario?.email ?: "Invitado"

            MuestraDatosScreen(
                navController = navController,
                username = username
            )
        }

        // 🔐 LOGIN
        composable(login) {
            LoginScreen(navController = navController)
        }

        // Drawer (si lo sigues usando)
        composable(
            route = drawer,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { bs ->
            val username = Uri.decode(bs.arguments?.getString("username").orEmpty())
            MuestraDatosScreen(
                navController = navController,
                username = username
            )
        }

        // 🛒 CATÁLOGO
        composable(
            route = catalogo,
            arguments = listOf(
                navArgument("categoria") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { bs ->
            val categoria = bs.arguments?.getString("categoria")

            CatalogScreen(
                navController = navController,
                categoria = categoria,
                cartViewModel = cartViewModel
            )
        }

        // 🧩 DETALLE PRODUCTO
        composable(
            route = product,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { bs ->
            val id = bs.arguments?.getString("id").orEmpty()

            ProductDetailScreen(
                navController = navController,
                codigo = Uri.decode(id),
                cartViewModel = cartViewModel
            )
        }

        // ➕ FORM PRODUCTO
        composable(
            route = productForm,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType }
            )
        ) { bs ->
            val nombre = Uri.decode(bs.arguments?.getString("nombre").orEmpty())
            val precio = bs.arguments?.getString("precio").orEmpty()

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio
            )
        }

        // 🛒 CARRITO REAL
        composable(cart) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        // 👤 PERFIL
        composable(profile) {
            ProfileScreen(
                navController = navController,
                emailFromSession = SessionManager.usuarioActual?.email
            )
        }

        // ⚙️ CONFIGURACIÓN DE CUENTA
        composable(accountSettings) {
            AccountSettingsScreen(
                navController = navController,
                emailFromSession = SessionManager.usuarioActual?.email
            )
        }

        // 📰 BLOG
        composable(blog) {
            BlogScreen(navController = navController)
        }

        // 🎫 EVENTOS
        composable(events) {
            Text("Eventos")
        }

        // 📝 REGISTRO
        composable(register) {
            RegisterScreen(
                nav = navController,
                onDone = {
                    navController.navigate(home) {
                        popUpTo(home) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // 📷 QR SCANNER
        composable(qrScanner) {
            QrScannerScreen(
                viewModel = qrViewModel,
                hasCameraPermission = hasCameraPermission,
                onRequestPermission = onRequestPermission,
                navController = navController
            )
        }
    }
}
