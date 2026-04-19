package com.example.turtlewowcompanion.ui.screens.bosses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.domain.model.LootItem
import com.example.turtlewowcompanion.ui.common.DetailRow
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.HeroHeader
import com.example.turtlewowcompanion.ui.common.ShimmerLoadingScreen
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.WowGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BossDetailScreen(
    bossId: Long,
    container: AppContainer,
    onBack: () -> Unit,
    viewModel: BossViewModel = viewModel(factory = BossViewModel.Factory(container.bossRepository))
) {
    val state by viewModel.bossDetailState.collectAsState()

    LaunchedEffect(bossId) { viewModel.loadBossById(bossId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Jefe") },
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
        when (val s = state) {
            is UiState.Loading -> ShimmerLoadingScreen(Modifier.padding(padding))
            is UiState.Error -> ErrorScreen(
                message = s.message,
                onRetry = { viewModel.loadBossById(bossId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val boss = s.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    HeroHeader(
                        title = boss.name,
                        subtitle = boss.zoneName,
                        backgroundBrush = ThemeBrushes.npcs,
                        imageRes = R.drawable.img_npc_boss,
                        height = 180.dp
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        // Estadísticas
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            accentColor = WowGold
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                if (boss.level != null) DetailRow("Nivel", "${boss.level}")
                                if (boss.health != null) DetailRow("Vida", formatHealth(boss.health))
                                DetailRow("Ubicación", boss.zoneName)
                            }
                        }

                        // Descripción
                        if (boss.description.isNotBlank()) {
                            Spacer(Modifier.height(16.dp))
                            WowDivider()
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = boss.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Lore
                        if (boss.lore.isNotBlank()) {
                            Spacer(Modifier.height(16.dp))
                            Text("Historia", style = MaterialTheme.typography.titleMedium, color = WowGold)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = boss.lore,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Loot
                        if (boss.lootItems.isNotEmpty()) {
                            Spacer(Modifier.height(16.dp))
                            WowDivider()
                            Spacer(Modifier.height(16.dp))
                            Text("Botín", style = MaterialTheme.typography.titleMedium, color = WowGold)
                            Spacer(Modifier.height(8.dp))
                            boss.lootItems.forEach { item ->
                                LootItemCard(item)
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LootItemCard(item: LootItem) {
    val qualityColor = when (item.quality) {
        "POOR"      -> Color(0xFF9D9D9D)
        "COMMON"    -> Color.White
        "UNCOMMON"  -> Color(0xFF1EFF00)
        "RARE"      -> Color(0xFF0070DD)
        "EPIC"      -> Color(0xFFA335EE)
        "LEGENDARY" -> Color(0xFFFF8000)
        else        -> Color.White
    }
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        accentColor = qualityColor
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = qualityColor,
                    modifier = Modifier.weight(1f)
                )
                if (item.dropRate != null) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${String.format("%.1f", item.dropRate)}%",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (item.description.isNotBlank()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "${item.qualityLabel} · ${item.type}",
                style = MaterialTheme.typography.labelSmall,
                color = qualityColor.copy(alpha = 0.7f)
            )
        }
    }
}

private fun formatHealth(health: Long): String {
    return when {
        health >= 1_000_000 -> "${health / 1_000_000}M"
        health >= 1_000 -> "${health / 1_000}K"
        else -> "$health"
    }
}

