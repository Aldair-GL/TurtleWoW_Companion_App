package com.example.turtlewowcompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WowDarkColorScheme = darkColorScheme(
    primary = WowGold,
    onPrimary = DarkBackground,
    primaryContainer = WowBrownLight,
    onPrimaryContainer = WowGold,
    secondary = WowGoldDark,
    onSecondary = Color.White,
    secondaryContainer = WowBrown,
    onSecondaryContainer = Parchment,
    tertiary = ArcaneBlue,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = TextLight,
    surface = DarkSurface,
    onSurface = TextLight,
    surfaceVariant = DarkStone,
    onSurfaceVariant = TextDim,
    error = HordeRed,
    onError = Color.White,
    outline = WowBronze.copy(alpha = 0.5f)
)

private val WowLightColorScheme = lightColorScheme(
    primary = WowBrown,
    onPrimary = Color.White,
    primaryContainer = ParchmentDark,
    onPrimaryContainer = WowBrown,
    secondary = WowGoldDark,
    onSecondary = Color.White,
    secondaryContainer = Parchment,
    onSecondaryContainer = WowBrown,
    tertiary = AllianceBlue,
    onTertiary = Color.White,
    background = Parchment,
    onBackground = WowBrown,
    surface = Color.White,
    onSurface = WowBrown,
    surfaceVariant = ParchmentDark,
    onSurfaceVariant = WowBrownLight,
    error = HordeRed,
    onError = Color.White,
    outline = WowBronze.copy(alpha = 0.3f)
)

@Composable
fun TurtleWoWCompanionTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) WowDarkColorScheme else WowLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WowTypography,
        content = content
    )
}