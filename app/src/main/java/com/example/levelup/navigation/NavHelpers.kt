package com.example.levelup.navigation

import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun navToDrawer(nav: NavHostController, email: String) {
    val encoded = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
    nav.navigate("${Routes.Drawer}/$encoded") {
        popUpTo(Routes.Login) { inclusive = true }
        launchSingleTop = true
    }
}

fun navToCategory(nav: NavHostController, categoria: String?) {
    val encoded = categoria?.let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
    nav.navigate("${Routes.Catalogo}?categoria=${encoded ?: ""}") {
        launchSingleTop = true
    }
}

fun navToProduct(nav: NavHostController, id: String) {
    val encoded = URLEncoder.encode(id, StandardCharsets.UTF_8.toString())
    nav.navigate("${Routes.Product}/$encoded") {
        launchSingleTop = true
    }
}
