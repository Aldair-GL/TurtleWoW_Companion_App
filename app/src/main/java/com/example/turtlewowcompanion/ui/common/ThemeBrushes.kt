package com.example.turtlewowcompanion.ui.common

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.DarkSurface

/**
 * Pinceles de fondo temáticos para cada sección de la app.
 * Definidos directamente en Compose para mayor control sobre gradientes y colores.
 */
object ThemeBrushes {

    val homeHero = Brush.linearGradient(
        colors = listOf(
            Color(0xFF0F1A2E),
            Color(0xFF0A0F1A),
            Color(0xFF05080F)
        )
    )

    val zones = Brush.linearGradient(
        colors = listOf(
            Color(0xFF1A3A1A),
            Color(0xFF0F2E0F),
            Color(0xFF0A1A0A)
        )
    )

    val quests = Brush.linearGradient(
        colors = listOf(
            Color(0xFF2E1A0F),
            Color(0xFF1A0F05),
            Color(0xFF0F0A05)
        )
    )

    val npcs = Brush.linearGradient(
        colors = listOf(
            Color(0xFF1A1A2E),
            Color(0xFF0F0F1A),
            Color(0xFF0A0A0F)
        )
    )

    val defaultBg = Brush.verticalGradient(
        colors = listOf(DarkBackground, DarkSurface, DarkBackground)
    )
}



