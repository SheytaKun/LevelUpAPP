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
import com.example.levelup.ui.checkout.CheckoutScreen
import com.example.levelup.ui.home.MuestraDatosScreen
import com.example.levelup.ui.login.LoginScreen
import com.example.levelup.ui.newproducts.NewProductsScreen
import com.example.levelup.ui.nosotros.NosotrosScreen
import com.example.levelup.ui.notifications.NotificationsScreen
import com.example.levelup.ui.product.ProductDetailScreen
import com.example.levelup.ui.profile.ProfileScreen
import com.example.levelup.ui.profile.AccountSettingsScreen
import com.example.levelup.ui.register.RegisterScreen
import com.example.levelup.view.ProductoFormScreen
import com.example.levelup.view.QrScannerScreen
import com.example.levelup.viewmodel.CartViewModel
import com.example.levelup.viewmodel.QrViewModel
import com.example.levelup.viewmodel.OffersViewModel
import com.example.levelup.viewmodel.ProductoViewModel       // 👈 IMPORTANTE
import com.example.levelup.ui.offers.OffersScreen
import com.example.levelup.ui.special.SpecialDiscountsScreen
import com.example.levelup.ui.top.TopBuysScreen

@Composable
fun AppNav(
    navController: NavHostController,
    qrViewModel: QrViewModel,
    cartViewModel: CartViewModel,
    offersViewModel: OffersViewModel,
    productoViewModel: ProductoViewModel,          // 👈 NUEVO PARÁMETRO
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    LaunchedEffect(navController) {
        android.util.Log.d("NAV", "AppNav nav=${System.identityHashCode(navController)}")
    }

    val login            = "login"
    val home             = "home"
    val drawer           = "drawer/{username}"
    val catalogo         = "catalogo?categoria={categoria}"
    val offers           = "offers"
    val specialDiscounts = "special_discounts"
    val topBuys          = "top_buys"
    val newProducts      = "new_products"
    val product          = "product/{id}"
    val cart             = "cart"
    val checkout         = "checkout"
    val profile          = "profile"
    val accountSettings  = "account_settings"
    val blog             = "blog"
    val events           = "events"
    val register         = "register"
    val nosotros         = "nosotros"
    val productForm      = "producto_form/{nombre}/{precio}"
    val qrScanner        = "qrScanner"
    val notifications   =  "notifications"

    NavHost(navController = navController, startDestination = home) {

        composable(home) {
            val usuario = SessionManager.usuarioActual
            val username = usuario?.email ?: "Invitado"

            MuestraDatosScreen(
                navController = navController,
                username = username
            )
        }

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
                productoViewModel = productoViewModel,
                cartViewModel = cartViewModel
            )
        }

        // RUTA DE OFERTAS
        composable(offers) {
            OffersScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                offersViewModel = offersViewModel
            )
        }

        // ✅ RUTA DE DESCUENTOS ESPECIALES
        composable(specialDiscounts) {
            SpecialDiscountsScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                offersViewModel = offersViewModel
            )
        }

        // RUTA DE TOP COMPRAS
        composable(topBuys) {
            TopBuysScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                offersViewModel = offersViewModel
            )
        }

        composable(newProducts) {
            NewProductsScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(nosotros) {
            NosotrosScreen(
                navController = navController
            )
        }

        composable(notifications) {
            NotificationsScreen(
                navController = navController
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
                cartViewModel = cartViewModel,
                productoViewModel = productoViewModel       // 👈 TAMBIÉN AQUÍ
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

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio
            )
        }

        composable(cart) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(checkout) {
            CheckoutScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(profile) {
            ProfileScreen(
                navController = navController,
                emailFromSession = SessionManager.usuarioActual?.email
            )
        }

        composable(accountSettings) {
            AccountSettingsScreen(
                navController = navController,
                emailFromSession = SessionManager.usuarioActual?.email
            )
        }

        composable(blog) {
            BlogScreen(navController = navController)
        }

        composable(events) {
            Text("Eventos")
        }

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
