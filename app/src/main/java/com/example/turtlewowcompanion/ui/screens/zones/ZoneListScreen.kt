package com.example.turtlewowcompanion.ui.screens.zones

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.LoadingScreen
import com.example.turtlewowcompanion.ui.common.UiState
import com.example.turtlewowcompanion.ui.common.WowCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneListScreen(
    container: AppContainer,
    onZoneClick: (Long) -> Unit,
    onBack: () -> Unit,
    viewModel: ZoneViewModel = viewModel(factory = ZoneViewModel.Factory(container.zoneRepository))
) {
    val state by viewModel.zonesState.collectAsState()

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
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        when (val s = state) {
            is UiState.Loading -> LoadingScreen(Modifier.padding(padding))
            is UiState.Error -> ErrorScreen(
                message = s.message,
                onRetry = { viewModel.loadZones() },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(s.data, key = { it.id }) { zone ->
                        WowCard(
                            title = zone.name,
                            subtitle = "Nivel ${zone.level} · ${zone.faction}",
                            onClick = { onZoneClick(zone.id) }
                        )
                    }
                }
            }
        }
    }
}



