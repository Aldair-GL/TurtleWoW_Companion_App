package com.example.turtlewowcompanion.ui.screens.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.domain.model.FavoriteType
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
import com.example.turtlewowcompanion.ui.theme.NeutralFactionColors
import com.example.turtlewowcompanion.ui.theme.WowGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: Long,
    container: AppContainer,
    onBack: () -> Unit,
    viewModel: ItemViewModel = viewModel(factory = ItemViewModel.Factory(container.itemRepository))
) {
    val state by viewModel.detailState.collectAsState()
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(itemId) {
        viewModel.loadItemById(itemId)
        isFavorite = container.favoriteRepository.isFavorite(itemId, FavoriteType.ITEM)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Objeto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val item = (state as? UiState.Success)?.data ?: return@launch
                            container.favoriteRepository.toggleFavorite(
                                refId = item.id,
                                type = FavoriteType.ITEM,
                                name = item.name,
                                subtitle = "Objeto · ${item.qualityLabel}"
                            )
                            isFavorite = !isFavorite
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
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
                onRetry = { viewModel.loadItemById(itemId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val item = s.data
                val qualityColor = when (item.quality) {
                    "POOR"      -> Color(0xFF9D9D9D)
                    "COMMON"    -> Color.White
                    "UNCOMMON"  -> Color(0xFF1EFF00)
                    "RARE"      -> Color(0xFF0070DD)
                    "EPIC"      -> Color(0xFFA335EE)
                    "LEGENDARY" -> Color(0xFFFF8000)
                    else        -> Color.White
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    HeroHeader(
                        title = item.name,
                        subtitle = "${item.qualityLabel} · ${item.typeLabel}",
                        backgroundBrush = ThemeBrushes.npcs,
                        imageRes = R.drawable.img_hero_home,
                        height = 180.dp
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            accentColor = qualityColor
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                DetailRow("Calidad", item.qualityLabel)
                                DetailRow("Tipo", item.typeLabel)
                                if (item.subtype.isNotBlank()) {
                                    DetailRow("Subtipo", item.subtype)
                                }
                                if (item.itemLevel != null) {
                                    DetailRow("Nivel de objeto", "${item.itemLevel}")
                                }
                                if (item.levelRequired != null) {
                                    DetailRow("Nivel requerido", "${item.levelRequired}")
                                }
                                if (!item.professionName.isNullOrBlank()) {
                                    DetailRow("Profesión", item.professionName)
                                }
                            }
                        }

                        if (item.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            WowDivider(color = NeutralFactionColors.accent)
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

