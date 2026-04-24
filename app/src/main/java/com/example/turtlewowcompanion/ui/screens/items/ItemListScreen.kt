package com.example.turtlewowcompanion.ui.screens.items

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.common.*
import com.example.turtlewowcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    container: AppContainer,
    onItemClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: ItemViewModel = viewModel(factory = ItemViewModel.Factory(container.itemRepository))
) {
    val state by viewModel.listState.collectAsState()
    var selectedQuality by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }

    val qualityFilters = listOf("POOR", "COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY")
    val typeFilters = listOf("WEAPON", "ARMOR", "CONSUMABLE", "TRADE", "RECIPE", "MISC")
    val qualityColors = mapOf(
        "POOR" to Color(0xFF9D9D9D), "COMMON" to Color.White, "UNCOMMON" to Color(0xFF1EFF00),
        "RARE" to Color(0xFF0070DD), "EPIC" to Color(0xFFA335EE), "LEGENDARY" to Color(0xFFFF8000)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Objetos", style = MaterialTheme.typography.titleLarge) },
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
                onRetry = { viewModel.loadItems() },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val filtered = s.data.filter { item ->
                    (selectedQuality == null || item.quality == selectedQuality) &&
                    (selectedType == null || item.type == selectedType)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        HeroHeader(
                            title = "Objetos de Azeroth",
                            subtitle = "${s.data.size} objetos registrados",
                            backgroundBrush = ThemeBrushes.npcs,
                            imageRes = R.drawable.img_hero_home,
                            height = 140.dp
                        )
                    }

                    // Filtro por calidad
                    item {
                        Spacer(Modifier.height(4.dp))
                        Text("Calidad", style = MaterialTheme.typography.labelMedium, color = WowGold)
                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedQuality == null,
                                onClick = { selectedQuality = null },
                                label = { Text("Todas") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WowGold.copy(alpha = 0.3f),
                                    selectedLabelColor = WowGold
                                )
                            )
                            qualityFilters.forEach { q ->
                                FilterChip(
                                    selected = selectedQuality == q,
                                    onClick = { selectedQuality = if (selectedQuality == q) null else q },
                                    label = { Text(q.lowercase().replaceFirstChar { it.uppercase() }) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = (qualityColors[q] ?: WowGold).copy(alpha = 0.3f),
                                        selectedLabelColor = qualityColors[q] ?: WowGold
                                    )
                                )
                            }
                        }
                    }

                    // Filtro por tipo
                    item {
                        Text("Tipo", style = MaterialTheme.typography.labelMedium, color = WowGold)
                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedType == null,
                                onClick = { selectedType = null },
                                label = { Text("Todos") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WowGold.copy(alpha = 0.3f),
                                    selectedLabelColor = WowGold
                                )
                            )
                            typeFilters.forEach { t ->
                                FilterChip(
                                    selected = selectedType == t,
                                    onClick = { selectedType = if (selectedType == t) null else t },
                                    label = { Text(t.lowercase().replaceFirstChar { it.uppercase() }) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = WowGold.copy(alpha = 0.3f),
                                        selectedLabelColor = WowGold
                                    )
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            "${filtered.size} resultados",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(filtered, key = { it.id }) { item ->
                        val qColor = qualityColors[item.quality] ?: Color.White
                        WowCardEnhanced(
                            title = item.name,
                            subtitle = "${item.qualityLabel} · ${item.typeLabel}",
                            backgroundBrush = ThemeBrushes.npcs,
                            imageRes = null,
                            faction = "Neutral",
                            onClick = { onItemClick(item.id) }
                        )
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}
