package com.example.turtlewowcompanion.ui.screens.races

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
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
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.FactionBadge
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.HeroHeader
import com.example.turtlewowcompanion.ui.common.ShimmerLoadingScreen
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.common.racePortraitRes
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.FactionThemeProvider
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.LocalFactionColors
import com.example.turtlewowcompanion.ui.theme.WowGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RaceDetailScreen(
    raceId: Long,
    container: AppContainer,
    onBack: () -> Unit,
    viewModel: RaceViewModel = viewModel(factory = RaceViewModel.Factory(container.raceRepository))
) {
    val state by viewModel.raceDetailState.collectAsState()
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(raceId) {
        viewModel.loadRaceById(raceId)
        isFavorite = container.favoriteRepository.isFavorite(raceId, FavoriteType.RACE)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Raza") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val race = (state as? UiState.Success)?.data ?: return@launch
                            container.favoriteRepository.toggleFavorite(
                                refId = race.id,
                                type = FavoriteType.RACE,
                                name = race.name,
                                subtitle = "Raza · ${race.factionName}"
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
                onRetry = { viewModel.loadRaceById(raceId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val race = s.data
                FactionThemeProvider(faction = race.factionName) {
                    val factionColors = LocalFactionColors.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        HeroHeader(
                            title = race.name,
                            subtitle = race.factionName,
                            backgroundBrush = ThemeBrushes.quests,
                            imageRes = racePortraitRes(race.name) ?: R.drawable.img_hero_quests,
                            height = 180.dp
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            FactionBadge(faction = race.factionName)

                            Spacer(modifier = Modifier.height(12.dp))

                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                accentColor = factionColors.primary
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Clases disponibles",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = WowGold
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        race.availableClasses.forEach { className ->
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = factionColors.primary.copy(alpha = 0.25f),
                                                contentColor = factionColors.accent
                                            ) {
                                                Text(
                                                    text = className,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            WowDivider(color = factionColors.accent)
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = race.description,
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
