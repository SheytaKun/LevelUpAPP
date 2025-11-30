// kotlin
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
import com.example.levelup.view.QrScannerScreen
import com.example.levelup.viewmodel.QrViewModel
import com.example.levelup.ui.blog.BlogScreen
import com.example.levelup.ui.cart.CartScreen
import com.example.levelup.ui.catalog.CatalogScreen
import com.example.levelup.ui.home.MuestraDatosScreen
import com.example.levelup.ui.product.ProductDetailScreen
import com.example.levelup.ui.login.LoginScreen
import com.example.levelup.ui.register.RegisterScreen
import com.example.levelup.view.ProductoFormScreen
import com.example.levelup.ui.profile.ProfileScreen
import com.example.levelup.viewmodel.CartViewModel

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

    val login    = "login"
    val drawer   = "drawer/{username}"
    val catalogo = "catalogo?categoria={categoria}"
    val product  = "product/{id}"
    val cart     = "cart"
    val profile  = "profile"
    val blog     = "blog"
    val events   = "events"
    val register = "register"
    val productForm = "producto_form/{nombre}/{precio}"
    val qrScanner = "qrScanner"

    NavHost(navController = navController, startDestination = login) {

        composable(login) {
            LoginScreen(navController = navController)
        }

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

        composable(
            route = productForm,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType }
            )
        ) { bs ->
            val nombre = Uri.decode(bs.arguments?.getString("nombre").orEmpty())
            val precio = bs.arguments?.getString("precio").orEmpty()
            ProductoFormScreen(navController = navController, nombre = nombre, precio = precio)
        }

        composable(cart) { 
            CartScreen(navController = navController, viewModel = cartViewModel)
        }

        composable(profile) {
            ProfileScreen(
                navController = navController,
                emailFromSession = null
            )
        }

        composable(blog) {
            BlogScreen(navController = navController)
        }
        composable(events)  { Text("Eventos") }

        composable(register) {
            RegisterScreen(
                nav = navController,
                onDone = { email ->
                    navController.navigate("drawer/${Uri.encode(email)}") {
                        popUpTo(login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )

        }
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
