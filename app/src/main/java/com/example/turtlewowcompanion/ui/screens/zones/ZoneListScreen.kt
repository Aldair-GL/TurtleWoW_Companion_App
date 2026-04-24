package com.example.turtlewowcompanion.ui.screens.zones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.ui.common.HeroHeader
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.WowCardEnhanced
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.WowGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneListScreen(
    onNavigateToCitiesAlliance: () -> Unit,
    onNavigateToCitiesHorde: () -> Unit,
    onNavigateToDungeonsEK: () -> Unit,
    onNavigateToDungeonsKalimdor: () -> Unit,
    onNavigateToOpenWorldEK: () -> Unit,
    onNavigateToOpenWorldKalimdor: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Zonas", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlassSurface.copy(alpha = 0.9f),
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                HeroHeader(
                    title = "Zonas de Azeroth",
                    subtitle = "Explora el mundo de Warcraft",
                    backgroundBrush = ThemeBrushes.zones,
                    imageRes = R.drawable.img_hero_zones,
                    height = 140.dp
                )
            }

            // Mundo abierto
            item {
                Spacer(Modifier.height(4.dp))
                Text("Mundo abierto", style = MaterialTheme.typography.titleMedium, color = WowGold)
            }
            item {
                WowCardEnhanced(
                    title = "Reinos del Este",
                    subtitle = "Elwynn, Duskwood, Plaguelands y más",
                    backgroundBrush = ThemeBrushes.zones,
                    faction = "Alliance",
                    onClick = onNavigateToOpenWorldEK
                )
            }
            item {
                WowCardEnhanced(
                    title = "Kalimdor",
                    subtitle = "Barrens, Ashenvale, Tanaris y más",
                    backgroundBrush = ThemeBrushes.zones,
                    faction = "Horde",
                    onClick = onNavigateToOpenWorldKalimdor
                )
            }

            // Ciudades
            item {
                Spacer(Modifier.height(4.dp))
                Text("Ciudades capitales", style = MaterialTheme.typography.titleMedium, color = WowGold)
            }
            item {
                WowCardEnhanced(
                    title = "Ciudades de la Alianza",
                    subtitle = "Stormwind, Ironforge, Darnassus",
                    backgroundBrush = ThemeBrushes.quests,
                    faction = "Alliance",
                    onClick = onNavigateToCitiesAlliance
                )
            }
            item {
                WowCardEnhanced(
                    title = "Ciudades de la Horda",
                    subtitle = "Orgrimmar, Thunder Bluff, Undercity",
                    backgroundBrush = ThemeBrushes.quests,
                    faction = "Horde",
                    onClick = onNavigateToCitiesHorde
                )
            }

            // Mazmorras
            item {
                Spacer(Modifier.height(4.dp))
                Text("Mazmorras", style = MaterialTheme.typography.titleMedium, color = WowGold)
            }
            item {
                WowCardEnhanced(
                    title = "Mazmorras — Reinos del Este",
                    subtitle = "Deadmines, Stockade, Scholomance...",
                    backgroundBrush = ThemeBrushes.npcs,
                    faction = "Neutral",
                    onClick = onNavigateToDungeonsEK
                )
            }
            item {
                WowCardEnhanced(
                    title = "Mazmorras — Kalimdor",
                    subtitle = "Wailing Caverns, Dire Maul, Maraudon...",
                    backgroundBrush = ThemeBrushes.npcs,
                    faction = "Neutral",
                    onClick = onNavigateToDungeonsKalimdor
                )
            }
        }
    }
}
