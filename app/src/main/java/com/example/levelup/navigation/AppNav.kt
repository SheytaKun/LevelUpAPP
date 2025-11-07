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
import com.example.levelup.ui.register.RegisterScreen
import com.example.levelup.view.DrawerMenu
// 🔹 Descomenta cuando tengas la vista real de registro
// import com.example.levelup.ui.register.RegisterScreen

@Composable
fun AppNav(navController: NavHostController) {

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
    val forgot   = "forgot"

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

        // Opción A: usar tu RegisterScreen real (recomendada)

        composable(register) {
            RegisterScreen(
                nav = navController,
                onDone = { email ->
                    // al completar registro, entra al Drawer con el email
                    navController.navigate("drawer/${Uri.encode(email)}") {
                        popUpTo(login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }


//        // Opción B: placeholder temporal (quítalo cuando tengas la vista real)
//        composable(register) {
//            Text("Pantalla de Registro (WIP)")
//        }

        // (opcional) Recuperar contraseña
        composable(forgot) {
            Text("Recuperar contraseña (WIP)")
        }
    }
}
