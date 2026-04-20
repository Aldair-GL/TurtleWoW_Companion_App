package com.example.turtlewowcompanion.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.WowGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    container: AppContainer
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlassSurface.copy(alpha = 0.9f),
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Acerca de
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                accentColor = WowGold
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Acerca de",
                        style = MaterialTheme.typography.titleMedium,
                        color = WowGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Turtle WoW Companion v1.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Enciclopedia de World of Warcraft Vanilla",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Proyecto académico — Android + Spring Boot",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WowDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // Datos
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                accentColor = WowGold
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Datos",
                        style = MaterialTheme.typography.titleMedium,
                        color = WowGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Los datos se obtienen del servidor y se almacenan localmente para uso sin conexión.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                container.favoriteRepository.deleteAll()
                                container.searchRepository.clearHistory()
                                snackbarHostState.showSnackbar("Caché y favoritos borrados")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Borrar caché local y favoritos")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            WowDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // Tecnologías
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                accentColor = WowGold
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Tecnologías",
                        style = MaterialTheme.typography.titleMedium,
                        color = WowGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Kotlin + Jetpack Compose", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• Room (SQLite) para persistencia local", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• Retrofit 2 para conexión con API REST", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• Spring Boot (Backend)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• PostgreSQL en Neon Tech (Base de datos)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
