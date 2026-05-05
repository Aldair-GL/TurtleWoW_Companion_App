package com.example.turtlewowcompanion.ui.screens.zones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.HeroHeader
import com.example.turtlewowcompanion.ui.common.ImageMapper
import com.example.turtlewowcompanion.ui.common.ShimmerLoadingScreen
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowCardEnhanced
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneCategoryScreen(
    container: AppContainer,
    zoneType: String,
    title: String,
    continent: String? = null,
    factionFilter: String? = null,
    onZoneClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: ZoneViewModel = viewModel(factory = ZoneViewModel.Factory(container.zoneRepository))
) {
    val state by viewModel.zonesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, style = MaterialTheme.typography.titleLarge) },
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
                onRetry = { viewModel.loadZones() },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val filtered = s.data.filter { zone ->
                    val typeMatch = zone.zoneType.equals(zoneType, ignoreCase = true)
                    val continentMatch = continent == null ||
                        zone.continent.equals(continent, ignoreCase = true)
                    val factionMatch = factionFilter == null || matchesFaction(zone.faction, factionFilter)
                    typeMatch && continentMatch && factionMatch
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        HeroHeader(
                            title = title,
                            subtitle = "${filtered.size} zonas",
                            backgroundBrush = ThemeBrushes.zones,
                            imageRes = R.drawable.img_hero_zones,
                            height = 140.dp
                        )
                    }
                    if (filtered.isEmpty()) {
                        item {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "No hay zonas que mostrar en esta categoría.\n" +
                                    "Comprueba que el backend está activo y que la base de datos contiene zonas para los filtros aplicados.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                            )
                        }
                    }
                    items(filtered, key = { it.id }) { zone ->
                        WowCardEnhanced(
                            title = zone.name,
                            subtitle = "${zone.zoneTypeLabel} · Nv ${zone.level} · ${zone.faction}",
                            backgroundBrush = ImageMapper.zoneBrush(zone.name),
                            imageRes = ImageMapper.zoneImageRes(zone.name),
                            faction = zone.faction,
                            onClick = { onZoneClick(zone.id) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Acepta múltiples variantes de nombre de facción que pueda devolver el backend
 * (inglés, español, mayúsculas, abreviaturas) para que el filtrado nunca quede
 * vacío por una diferencia de cadena trivial.
 */
private fun matchesFaction(zoneFaction: String, target: String): Boolean {
    val zf = zoneFaction.lowercase().trim()
    return when (target.lowercase().trim()) {
        "alliance" -> zf.contains("alliance") || zf.contains("alianza") || zf == "ally"
        "horde" -> zf.contains("horde") || zf.contains("horda")
        else -> zf.contains(target, ignoreCase = true)
    }
}

