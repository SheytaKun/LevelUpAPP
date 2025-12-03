package com.example.levelup.ui.nosotros

import androidx.activity.ComponentActivity
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NosotrosScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun nosotrosScreen_muestraTituloYNombredelaTienda() {
        composeRule.setContent {
            val context = LocalContext.current
            val navController = remember { NavHostController(context) }
            NosotrosScreen(navController = navController)
        }

        composeRule.onNodeWithText("Nosotros").assertIsDisplayed()
        composeRule.onNodeWithText("LevelUp Store").assertIsDisplayed()
    }
}
