package com.example.levelup.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.login001v.ui.theme.Typography

private val LevelUpDarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E90FF),
    secondary = Color(0xFF39FF14),
    background = Color(0xFF000000),
    surface = Color(0xFF18181C),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LevelUpTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LevelUpDarkColorScheme,
        typography = Typography,
        content = content
    )

}
