package com.example.turtlewowcompanion.ui.screens.zones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Shield
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneListScreen(
    onNavigateToCities: () -> Unit,
    onNavigateToDungeons: () -> Unit,
    onNavigateToOpenWorld: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
            item {
                WowCardEnhanced(
                    title = "Mundo abierto",
                    subtitle = "Regiones, bosques, desiertos y mas",
                    backgroundBrush = ThemeBrushes.zones,
                    imageRes = null,
                    faction = "Neutral",
                    onClick = onNavigateToOpenWorld
                )
            }
            item {
                WowCardEnhanced(
                    title = "Ciudades",
                    subtitle = "Capitales de Alianza y Horda",
                    backgroundBrush = ThemeBrushes.quests,
                    imageRes = null,
                    faction = "Neutral",
                    onClick = onNavigateToCities
                )
            }
            item {
                WowCardEnhanced(
                    title = "Mazmorras",
                    subtitle = "Instancias de grupo con jefes y botin",
                    backgroundBrush = ThemeBrushes.npcs,
                    imageRes = null,
                    faction = "Neutral",
                    onClick = onNavigateToDungeons
                )
            }
        }
    }
}
