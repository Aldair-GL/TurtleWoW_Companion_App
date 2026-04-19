package com.example.turtlewowcompanion.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.turtlewowcompanion.R

data class FactionColors(
    val primary: Color,
    val accent: Color,
    val background: Color,
    val iconRes: Int
)

val HordeFactionColors = FactionColors(
    primary = HordeRed,
    accent = HordeGold,
    background = HordeBackground,
    iconRes = R.drawable.ic_horde
)

val AllianceFactionColors = FactionColors(
    primary = AllianceBlue,
    accent = AllianceGold,
    background = AllianceBackground,
    iconRes = R.drawable.ic_alliance
)

val NeutralFactionColors = FactionColors(
    primary = WowBronze,
    accent = WowGold,
    background = DarkBackground,
    iconRes = R.drawable.ic_neutral
)

val LocalFactionColors = staticCompositionLocalOf { NeutralFactionColors }

fun factionColorsFor(faction: String): FactionColors {
    return when {
        faction.contains("horde", ignoreCase = true) ||
        faction.contains("horda", ignoreCase = true) -> HordeFactionColors
        faction.contains("alliance", ignoreCase = true) ||
        faction.contains("alianza", ignoreCase = true) -> AllianceFactionColors
        else -> NeutralFactionColors
    }
}

@Composable
fun FactionThemeProvider(
    faction: String,
    content: @Composable () -> Unit
) {
    val colors = factionColorsFor(faction)
    CompositionLocalProvider(LocalFactionColors provides colors) {
        content()
    }
}

