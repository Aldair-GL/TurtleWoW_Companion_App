package com.example.turtlewowcompanion.ui.screens.bosses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BossListScreen(
    zoneId: Long,
    zoneName: String,
    container: AppContainer,
    onBossClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: BossViewModel = viewModel(factory = BossViewModel.Factory(container.bossRepository))
) {
    val state by viewModel.bossesState.collectAsState()

    LaunchedEffect(zoneId) { viewModel.loadZoneBosses(zoneId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jefes", style = MaterialTheme.typography.titleLarge) },
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
                onRetry = { viewModel.loadZoneBosses(zoneId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        HeroHeader(
                            title = zoneName,
                            subtitle = "${s.data.size} jefes de mazmorra",
                            backgroundBrush = ThemeBrushes.npcs,
                            imageRes = R.drawable.img_npc_boss,
                            height = 140.dp
                        )
                    }
                    items(s.data, key = { it.id }) { boss ->
                        WowCardEnhanced(
                            title = boss.name,
                            subtitle = if (boss.level != null) "Nivel ${boss.level}" else "Jefe",
                            backgroundBrush = ThemeBrushes.npcs,
                            imageRes = null,
                            faction = "Neutral",
                            onClick = { onBossClick(boss.id) }
                        )
                    }
                }
            }
        }
    }
}

