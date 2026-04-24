package com.example.turtlewowcompanion.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.common.GlassCard
import com.example.turtlewowcompanion.ui.common.WowDivider
import com.example.turtlewowcompanion.ui.common.racePortraitRes
import com.example.turtlewowcompanion.ui.common.classPortraitRes
import com.example.turtlewowcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: Long,
    username: String,
    container: AppContainer,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory(container.userRepository))
) {
    val characters by viewModel.characters.collectAsState()
    val dungeonProgress by viewModel.dungeonProgress.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userId) { viewModel.loadForUser(userId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = HordeRed)
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cabecera de usuario
            item {
                GlassCard(modifier = Modifier.fillMaxWidth(), accentColor = WowGold) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = WowGold,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(username, style = MaterialTheme.typography.headlineSmall, color = WowGold)
                            Text(
                                "${characters.size} personajes · $completedCount mazmorras completadas",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Sección personajes
            item {
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Mis personajes", style = MaterialTheme.typography.titleMedium, color = WowGold)
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir personaje", tint = WowGold)
                    }
                }
            }

            if (characters.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "No tienes personajes aún. Pulsa + para crear uno.",
                            modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(characters, key = { it.id }) { char ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        accentColor = when {
                            char.raceName.lowercase().let {
                                it.contains("orc") || it.contains("undead") || it.contains("troll") || it.contains("tauren")
                            } -> HordeRed
                            else -> AllianceBlue
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(char.name, style = MaterialTheme.typography.titleMedium, color = WowGold)
                                Text(
                                    "${char.raceName} ${char.className} — Nivel ${char.level}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { viewModel.deleteCharacter(char.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = HordeRed.copy(alpha = 0.7f))
                            }
                        }
                    }
                }
            }

            // Sección progreso de mazmorras
            item {
                Spacer(Modifier.height(8.dp))
                WowDivider()
                Spacer(Modifier.height(8.dp))
                Text("Progreso de mazmorras", style = MaterialTheme.typography.titleMedium, color = WowGold)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Marca las mazmorras que has completado desde el detalle de cada mazmorra.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (dungeonProgress.isNotEmpty()) {
                items(dungeonProgress, key = { it.id }) { dp ->
                    GlassCard(modifier = Modifier.fillMaxWidth(), accentColor = if (dp.completed) FelGreen else GlassBorder) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                if (dp.completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (dp.completed) FelGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                dp.zoneName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (dp.completed) FelGreen else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Entra en una mazmorra y márcala como completada.",
                            modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }

    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, race, cls, level ->
                viewModel.addCharacter(userId, name, race, cls, level)
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCharacterDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, race: String, className: String, level: Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedRace by remember { mutableStateOf("Human") }
    var selectedClass by remember { mutableStateOf("Warrior") }
    var level by remember { mutableStateOf("1") }
    var raceExpanded by remember { mutableStateOf(false) }
    var classExpanded by remember { mutableStateOf(false) }

    val races = listOf("Human", "Dwarf", "Night Elf", "Gnome", "Orc", "Undead", "Tauren", "Troll")
    val classes = listOf("Warrior", "Paladin", "Hunter", "Rogue", "Priest", "Shaman", "Mage", "Warlock", "Druid")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        titleContentColor = WowGold,
        title = { Text("Nuevo personaje") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WowGold,
                        unfocusedBorderColor = GlassBorder,
                        cursorColor = WowGold,
                        focusedLabelColor = WowGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Raza selector
                ExposedDropdownMenuBox(expanded = raceExpanded, onExpandedChange = { raceExpanded = it }) {
                    OutlinedTextField(
                        value = selectedRace,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Raza") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = raceExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WowGold,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = WowGold
                        ),
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = raceExpanded, onDismissRequest = { raceExpanded = false }) {
                        races.forEach { r ->
                            DropdownMenuItem(
                                text = { Text(r) },
                                onClick = { selectedRace = r; raceExpanded = false }
                            )
                        }
                    }
                }

                // Clase selector
                ExposedDropdownMenuBox(expanded = classExpanded, onExpandedChange = { classExpanded = it }) {
                    OutlinedTextField(
                        value = selectedClass,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Clase") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = classExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WowGold,
                            unfocusedBorderColor = GlassBorder,
                            focusedLabelColor = WowGold
                        ),
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = classExpanded, onDismissRequest = { classExpanded = false }) {
                        classes.forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c) },
                                onClick = { selectedClass = c; classExpanded = false }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = level,
                    onValueChange = { level = it.filter { c -> c.isDigit() }.take(2) },
                    label = { Text("Nivel (1-60)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WowGold,
                        unfocusedBorderColor = GlassBorder,
                        cursorColor = WowGold,
                        focusedLabelColor = WowGold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val lvl = (level.toIntOrNull() ?: 1).coerceIn(1, 60)
                    if (name.isNotBlank()) onConfirm(name, selectedRace, selectedClass, lvl)
                },
                colors = ButtonDefaults.buttonColors(containerColor = WowGold, contentColor = DarkBackground)
            ) { Text("Crear") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = WowGold.copy(alpha = 0.7f))
            }
        }
    )
}


