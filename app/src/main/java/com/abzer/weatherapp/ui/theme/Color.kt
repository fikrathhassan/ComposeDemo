package com.abzer.weatherapp.ui.theme

import androidx.compose.ui.graphics.Color

// DarkColors
val darkPrimary = Color(0xFFD0BCFF)
val darkOnPrimary = Color(0xFF000000)
val darkCardBackground = Color(0xFF000000)
val darkSecondary = Color(0xFFCCC2DC)
val darkOnSecondary = Color(0xFF000000)
val darkTertiary = Color(0xFFEFB8C8)
val darkOnTertiary = Color(0xFF000000)

// LightColors
val lightPrimary = Color(0xFF6650a4)
val lightOnPrimary = Color(0xFFFFFFFF)
val lightCardBackground = Color(0xFFFFFFFF)
val lightSecondary = Color(0xFF625b71)
val lightOnSecondary = Color(0xFFFFFFFF)
val lightTertiary = Color(0xFF7D5260)
val lightOnTertiary = Color(0xFFFFFFFF)

data class Colors(
    val primary: Color = Color.Unspecified,
    val textPrimary: Color = Color.Unspecified,
    val cardBackground: Color = Color.Unspecified,
)

val darkColors = Colors(
    primary = darkPrimary,
    textPrimary = darkOnPrimary,
    cardBackground = darkCardBackground,
)

val lightColors = Colors(
    primary = lightPrimary,
    textPrimary = lightOnPrimary,
    cardBackground = lightCardBackground,
)