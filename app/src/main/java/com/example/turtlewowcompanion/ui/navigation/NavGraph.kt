package com.example.turtlewowcompanion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.screens.favorites.FavoritesScreen
import com.example.turtlewowcompanion.ui.screens.home.HomeScreen
import com.example.turtlewowcompanion.ui.screens.npcs.NpcDetailScreen
import com.example.turtlewowcompanion.ui.screens.npcs.NpcListScreen
import com.example.turtlewowcompanion.ui.screens.quests.QuestDetailScreen
import com.example.turtlewowcompanion.ui.screens.quests.QuestListScreen
import com.example.turtlewowcompanion.ui.screens.search.SearchScreen
import com.example.turtlewowcompanion.ui.screens.settings.SettingsScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneDetailScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    container: AppContainer
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToZones = { navController.navigate(Screen.ZoneList.route) },
                onNavigateToQuests = { navController.navigate(Screen.QuestList.route) },
                onNavigateToNpcs = { navController.navigate(Screen.NpcList.route) }
            )
        }

        // Zones
        composable(Screen.ZoneList.route) {
            ZoneListScreen(
                container = container,
                onZoneClick = { id -> navController.navigate(Screen.ZoneDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ZoneDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            ZoneDetailScreen(
                zoneId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // Quests
        composable(Screen.QuestList.route) {
            QuestListScreen(
                container = container,
                onQuestClick = { id -> navController.navigate(Screen.QuestDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.QuestDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            QuestDetailScreen(
                questId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // NPCs
        composable(Screen.NpcList.route) {
            NpcListScreen(
                container = container,
                onNpcClick = { id -> navController.navigate(Screen.NpcDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.NpcDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            NpcDetailScreen(
                npcId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // Search
        composable(Screen.Search.route) {
            SearchScreen(
                container = container,
                onResultClick = { type, id ->
                    when (type) {
                        "zone" -> navController.navigate(Screen.ZoneDetail.createRoute(id))
                        "quest" -> navController.navigate(Screen.QuestDetail.createRoute(id))
                        "npc" -> navController.navigate(Screen.NpcDetail.createRoute(id))
                    }
                }
            )
        }

        // Favorites
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                container = container,
                onItemClick = { type, refId ->
                    when (type) {
                        "ZONE" -> navController.navigate(Screen.ZoneDetail.createRoute(refId))
                        "QUEST" -> navController.navigate(Screen.QuestDetail.createRoute(refId))
                        "NPC" -> navController.navigate(Screen.NpcDetail.createRoute(refId))
                    }
                }
            )
        }

        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(container = container)
        }
    }
}
