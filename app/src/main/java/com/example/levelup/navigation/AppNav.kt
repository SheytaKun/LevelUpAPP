package com.example.levelup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelup.ui.login.LoginScreen
import com.example.levelup.view.DrawerMenu
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.Login) {
        composable(Routes.Login) { LoginScreen(navController = nav) }

        composable(
            route = Routes.DrawerPath,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val raw = backStack.arguments?.getString("username").orEmpty()
            val username = try {
                URLDecoder.decode(raw, StandardCharsets.UTF_8.toString())
            } catch (_: Exception) { raw }

            DrawerMenu(username = username, navController = nav)
        }

        // Catálogo, Producto, etc...
        composable(
            route = Routes.CatalogoPath,
            arguments = listOf(navArgument("categoria") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStack ->
            val categoria = backStack.arguments?.getString("categoria")
            androidx.compose.material3.Text("Catálogo: ${categoria ?: "todas"}")
        }

        composable(Routes.ProductPath, listOf(navArgument("id") { type = NavType.StringType })) {
            val id = it.arguments?.getString("id").orEmpty()
            androidx.compose.material3.Text("Producto $id")
        }
    }
}
