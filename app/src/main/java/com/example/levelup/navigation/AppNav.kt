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
import com.example.levelup.ui.login.LoginScreen
import com.example.levelup.view.DrawerMenu

@Composable
fun AppNav(navController: NavHostController) {

    // LOG: debe ser IGUAL al de MainActivity
    LaunchedEffect(navController) {
        android.util.Log.d("NAV", "AppNav      nav=${System.identityHashCode(navController)}")
    }

    // Rutas inline
    val login    = "login"
    val drawer   = "drawer/{username}"
    val catalogo = "catalogo?categoria={categoria}"
    val product  = "product/{id}"
    val cart     = "cart"
    val profile  = "profile"
    val blog     = "blog"
    val events   = "events"

    NavHost(navController = navController, startDestination = login) {

        composable(login) {
            LoginScreen(navController = navController)
        }

        composable(
            route = drawer,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { bs ->
            val username = Uri.decode(bs.arguments?.getString("username").orEmpty())
            DrawerMenu(username = username, navController = navController)
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
            Text("Catálogo: ${categoria ?: "todas"}")
        }

        composable(
            route = product,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { bs ->
            val id = bs.arguments?.getString("id").orEmpty()
            Text("Producto $id")
        }

        composable(cart)    { Text("Carrito") }
        composable(profile) { Text("Perfil") }
        composable(blog)    { Text("Blog") }
        composable(events)  { Text("Eventos") }
    }
}
