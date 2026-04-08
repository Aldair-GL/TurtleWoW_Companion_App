package com.example.turtlewowcompanion.ui.screens.quests

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.R
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.domain.model.FavoriteType
import com.example.turtlewowcompanion.ui.common.DetailRow
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.FactionBadge
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.HeroHeader
import com.example.turtlewowcompanion.ui.common.ImageMapper
import com.example.turtlewowcompanion.ui.common.ShimmerLoadingScreen
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.FactionThemeProvider
import com.example.turtlewowcompanion.ui.theme.LocalFactionColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    questId: Long,
    container: AppContainer,
    onBack: () -> Unit,
    viewModel: QuestViewModel = viewModel(factory = QuestViewModel.Factory(container.questRepository))
) {
    val state by viewModel.questDetailState.collectAsState()
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(questId) {
        viewModel.loadQuestById(questId)
        isFavorite = container.favoriteRepository.isFavorite(questId, FavoriteType.QUEST)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Misión") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val quest = (state as? UiState.Success)?.data ?: return@launch
                            container.favoriteRepository.toggleFavorite(
                                refId = quest.id,
                                type = FavoriteType.QUEST,
                                name = quest.title,
                                subtitle = "Quest · Nivel ${quest.level}"
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
                onRetry = { viewModel.loadQuestById(questId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val quest = s.data
                FactionThemeProvider(faction = quest.faction) {
                    val factionColors = LocalFactionColors.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        HeroHeader(
                            title = quest.title,
                            subtitle = "Nivel ${quest.level} · ${quest.zone}",
                            backgroundBrush = ImageMapper.questBrush(quest.zone),
                            imageRes = ImageMapper.questImageRes(quest.zone) ?: R.drawable.img_hero_quests,
                            height = 180.dp
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            FactionBadge(faction = quest.faction)

                            Spacer(modifier = Modifier.height(12.dp))

                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                accentColor = factionColors.primary
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    DetailRow("Nivel", "${quest.level}")
                                    DetailRow("Nivel mínimo", "${quest.minLevel}")
                                    DetailRow("Zona", quest.zone)
                                    DetailRow("Facción", quest.faction)
                                    if (quest.rewardXp > 0) {
                                        DetailRow("XP de recompensa", "${quest.rewardXp}")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            WowDivider(color = factionColors.accent)
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = quest.description,
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



