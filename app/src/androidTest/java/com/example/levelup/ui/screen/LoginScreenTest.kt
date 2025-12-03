package com.example.levelup.ui.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loginScreen_muestraTituloYBotonesPrincipales() {
        composeRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        // TopAppBar (según tu LoginScreen)
        composeRule.onNodeWithText("Level-Up Gamer").assertIsDisplayed()

        // Campos importantes
        composeRule.onNodeWithText("Usuario / Correo").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Acciones inferiores
        composeRule.onNodeWithText("¿Olvidaste tu contraseña?").assertIsDisplayed()
        composeRule.onNodeWithText("Crear cuenta").assertIsDisplayed()
    }
}
