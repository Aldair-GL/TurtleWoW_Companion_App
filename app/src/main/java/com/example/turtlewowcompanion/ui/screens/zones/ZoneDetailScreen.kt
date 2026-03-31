package com.example.turtlewowcompanion.ui.screens.zones

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.domain.model.FavoriteType
import com.example.turtlewowcompanion.ui.common.ErrorScreen
import com.example.turtlewowcompanion.ui.common.LoadingScreen
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneDetailScreen(
    zoneId: Long,
    container: AppContainer,
    onBack: () -> Unit,
    viewModel: ZoneViewModel = viewModel(factory = ZoneViewModel.Factory(container.zoneRepository))
) {
    val state by viewModel.zoneDetailState.collectAsState()
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(zoneId) {
        viewModel.loadZoneById(zoneId)
        isFavorite = container.favoriteRepository.isFavorite(zoneId, FavoriteType.ZONE)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Zona") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val zone = (state as? UiState.Success)?.data ?: return@launch
                            container.favoriteRepository.toggleFavorite(
                                refId = zone.id,
                                type = FavoriteType.ZONE,
                                name = zone.name,
                                subtitle = "Zona · Nivel ${zone.level}"
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
                onRetry = { viewModel.loadZoneById(zoneId) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                val zone = s.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = zone.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            DetailRow("Nivel", zone.level)
                            DetailRow("Facción", zone.faction)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = zone.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



