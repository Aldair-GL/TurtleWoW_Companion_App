package com.example.turtlewowcompanion.ui.screens.races

import androidx.compose.foundation.layout.*
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
import com.example.turtlewowcompanion.ui.common.ShimmerLoadingScreen
import com.example.turtlewowcompanion.ui.common.ThemeBrushes
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowCardEnhanced
import com.example.turtlewowcompanion.ui.common.racePortraitRes
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.AllianceBlue
import com.example.turtlewowcompanion.ui.theme.HordeRed
import com.example.turtlewowcompanion.ui.theme.WowGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceListScreen(
    container: AppContainer,
    onRaceClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: RaceViewModel = viewModel(factory = RaceViewModel.Factory(container.raceRepository))
) {
    val state by viewModel.racesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Razas", style = MaterialTheme.typography.titleLarge) },
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
                onRetry = { viewModel.loadRaces() },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val alliance = s.data.filter { it.factionName.contains("Alliance", ignoreCase = true) }
                val horde = s.data.filter { it.factionName.contains("Horde", ignoreCase = true) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        HeroHeader(
                            title = "Razas de Azeroth",
                            subtitle = "${s.data.size} razas jugables",
                            backgroundBrush = ThemeBrushes.quests,
                            imageRes = R.drawable.img_hero_quests,
                            height = 140.dp
                        )
                    }

                    // Alianza
                    item {
                        Spacer(Modifier.height(4.dp))
                        Text("Alianza", style = MaterialTheme.typography.titleMedium, color = AllianceBlue)
                    }
                    items(alliance, key = { it.id }) { race ->
                        WowCardEnhanced(
                            title = race.name,
                            subtitle = race.factionName,
                            backgroundBrush = ThemeBrushes.quests,
                            imageRes = racePortraitRes(race.name),
                            faction = race.factionName,
                            onClick = { onRaceClick(race.id) }
                        )
                    }

                    // Horda
                    item {
                        Spacer(Modifier.height(4.dp))
                        Text("Horda", style = MaterialTheme.typography.titleMedium, color = HordeRed)
                    }
                    items(horde, key = { it.id }) { race ->
                        WowCardEnhanced(
                            title = race.name,
                            subtitle = race.factionName,
                            backgroundBrush = ThemeBrushes.quests,
                            imageRes = racePortraitRes(race.name),
                            faction = race.factionName,
                            onClick = { onRaceClick(race.id) }
                        )
                    }
                }
            }
        }
    }
}

