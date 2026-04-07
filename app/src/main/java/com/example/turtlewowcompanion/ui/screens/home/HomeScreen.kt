package com.example.turtlewowcompanion.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GradientDark
import com.example.turtlewowcompanion.ui.theme.GradientMid
import com.example.turtlewowcompanion.ui.theme.GradientTransparent
import com.example.turtlewowcompanion.ui.theme.WowGold

@Composable
fun HomeScreen(
    onNavigateToZones: () -> Unit,
    onNavigateToQuests: () -> Unit,
    onNavigateToNpcs: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(ThemeBrushes.homeHero)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(GradientTransparent, GradientMid, GradientDark),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "Turtle WoW",
                    style = MaterialTheme.typography.displayLarge,
                    color = WowGold
                )
                Text(
                    text = "Enciclopedia de Azeroth",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        WowDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Sección de categorías
        Text(
            text = "Explorar",
            style = MaterialTheme.typography.headlineMedium,
            color = WowGold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Elige una categoría para comenzar tu aventura",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Grid de categorías
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCategoryCardEnhanced(
                title = "Zonas",
                description = "Explora las tierras de Azeroth",
                icon = Icons.Default.Explore,
                backgroundBrush = ThemeBrushes.zones,
                onClick = onNavigateToZones
            )
            HomeCategoryCardEnhanced(
                title = "Misiones",
                description = "Cadenas de quests y aventuras épicas",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                backgroundBrush = ThemeBrushes.quests,
                onClick = onNavigateToQuests
            )
            HomeCategoryCardEnhanced(
                title = "Personajes",
                description = "NPCs, vendedores y jefes de mazmorra",
                icon = Icons.Default.People,
                backgroundBrush = ThemeBrushes.npcs,
                onClick = onNavigateToNpcs
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        WowDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Info inferior
        Text(
            text = "Tu guía completa del World of Warcraft Vanilla",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun HomeCategoryCardEnhanced(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundBrush: Brush,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        accentColor = WowGold,
        onClick = onClick
    ) {
        // Fondo temático
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(backgroundBrush)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(GradientDark, GradientTransparent)
                    )
                )
        )

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = WowGold,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = WowGold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}



