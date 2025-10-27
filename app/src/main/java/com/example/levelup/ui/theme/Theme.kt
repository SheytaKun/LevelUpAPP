package com.example.levelup.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
private val LevelUpColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = NeonGreen,
    background = Black,
    surface = Black,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = White,
    onSurface = White
)


@Composable
fun LevelUpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LevelUpColorScheme,
        typography = AppTypography,
        content = content
    )
}