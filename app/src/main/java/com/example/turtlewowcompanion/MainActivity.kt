package com.example.turtlewowcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turtlewowcompanion.data.GuideCategory
import com.example.turtlewowcompanion.data.local.GuideEntryEntity
import com.example.turtlewowcompanion.ui.GuideViewModel
import com.example.turtlewowcompanion.ui.theme.TurtleWoWCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TurtleWoWCompanionTheme {
                GuideApp()
            }
        }
    }
}

@Composable
private fun GuideApp(viewModel: GuideViewModel = viewModel()) {
    val selected by viewModel.selectedCategory.collectAsState()
    val entries by viewModel.entries.collectAsState()

    GuideScreen(
        selected = selected,
        categories = GuideCategory.entries.toList(),
        entries = entries,
        onCategorySelected = viewModel::onCategorySelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuideScreen(
    selected: GuideCategory,
    categories: List<GuideCategory>,
    entries: List<GuideEntryEntity>,
    onCategorySelected: (GuideCategory) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(stringResource(R.string.screen_title)) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = categories.indexOf(selected)) {
                categories.forEach { category ->
                    Tab(
                        selected = category == selected,
                        onClick = { onCategorySelected(category) },
                        text = { Text(category.label) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(entries, key = { it.id }) { item ->
                    Card(
                        colors = CardDefaults.cardColors(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(text = item.title)
                            Text(text = item.description)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuidePreview() {
    TurtleWoWCompanionTheme {
        GuideScreen(
            selected = GuideCategory.OBJECTS,
            categories = GuideCategory.entries.toList(),
            entries = GuideRepositoryPreviewData,
            onCategorySelected = {}
        )
    }
}

private val GuideRepositoryPreviewData = listOf(
    GuideEntryEntity("preview-1", GuideCategory.OBJECTS.name, "Bolsa de viajero", "Aumenta el espacio inicial."),
    GuideEntryEntity("preview-2", GuideCategory.OBJECTS.name, "Piedra de hogar", "Vuelve rápido a una posada.")
)
