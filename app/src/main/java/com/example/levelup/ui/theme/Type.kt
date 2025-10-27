package com.example.levelup.ui.theme

import androidx.compose.material3.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp

private val provider = Provider(
    authority = "com.google.android.gms.fonts",
    packageName = "com.google.android.gms",
    certificates = R.font.google_fonts_certs
)

private val orbitron = GoogleFont("Orbitron")
private val roboto = GoogleFont("Roboto")

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = GoogleFontTypeface(orbitron, provider),
        fontSize = 34.sp,
        lineHeight = 38.sp,
        color = White
    ),
    headlineMedium = TextStyle(
        fontFamily = GoogleFontTypeface(orbitron, provider),
        fontSize = 24.sp,
        lineHeight = 28.sp,
        color = White
    ),
    bodyLarge = TextStyle(
        fontFamily = GoogleFontTypeface(roboto, provider),
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = White
    ),
    bodyMedium = TextStyle(
        fontFamily = GoogleFontTypeface(roboto, provider),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = LightGray
    ),
    labelLarge = TextStyle(
        fontFamily = GoogleFontTypeface(roboto, provider),
        fontSize = 14.sp
    ),
)