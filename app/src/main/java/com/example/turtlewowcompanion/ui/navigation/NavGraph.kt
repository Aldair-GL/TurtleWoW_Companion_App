package com.example.turtlewowcompanion.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.screens.classes.ClassDetailScreen
import com.example.turtlewowcompanion.ui.screens.classes.ClassListScreen
import com.example.turtlewowcompanion.ui.screens.favorites.FavoritesScreen
import com.example.turtlewowcompanion.ui.screens.home.HomeScreen
import com.example.turtlewowcompanion.ui.screens.npcs.NpcDetailScreen
import com.example.turtlewowcompanion.ui.screens.npcs.NpcListScreen
import com.example.turtlewowcompanion.ui.screens.quests.QuestDetailScreen
import com.example.turtlewowcompanion.ui.screens.quests.QuestListScreen
import com.example.turtlewowcompanion.ui.screens.races.RaceDetailScreen
import com.example.turtlewowcompanion.ui.screens.races.RaceListScreen
import com.example.turtlewowcompanion.ui.screens.search.SearchScreen
import com.example.turtlewowcompanion.ui.screens.settings.SettingsScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneDetailScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneListScreen

private const val ANIM_DURATION = 350

@Composable
fun NavGraph(
    navController: NavHostController,
    container: AppContainer
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) }
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToZones = { navController.navigate(Screen.ZoneList.route) },
                onNavigateToRaces = { navController.navigate(Screen.RaceList.route) },
                onNavigateToClasses = { navController.navigate(Screen.ClassList.route) }
            )
        }

        // ── Zonas ──────────────────────────────────────────────────────────
        composable(
            route = Screen.ZoneList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
            popEnterTransition = { fadeIn(tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) {
            ZoneListScreen(
                container = container,
                onZoneClick = { id -> navController.navigate(Screen.ZoneDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ZoneDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            ZoneDetailScreen(
                zoneId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // ── Razas ──────────────────────────────────────────────────────────
        composable(
            route = Screen.RaceList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
            popEnterTransition = { fadeIn(tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) {
            RaceListScreen(
                container = container,
                onRaceClick = { id -> navController.navigate(Screen.RaceDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.RaceDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            RaceDetailScreen(
                raceId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // ── Clases ─────────────────────────────────────────────────────────
        composable(
            route = Screen.ClassList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
            popEnterTransition = { fadeIn(tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) {
            ClassListScreen(
                container = container,
                onClassClick = { id -> navController.navigate(Screen.ClassDetail.createRoute(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ClassDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            ClassDetailScreen(
                classId = id,
                container = container,
                onBack = { navController.popBackStack() }
            )
        }

        // ── Legacy: Quests / NPCs (para favoritos y búsqueda guardada) ─────
        composable(
            route = Screen.QuestList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) {
            QuestListScreen(
                container = container,
                onQuestClick = {},
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
        composable(
            route = Screen.NpcList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) }
        ) {
            NpcListScreen(
                container = container,
                onNpcClick = {},
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

        // ── Búsqueda ────────────────────────────────────────────────────────
        composable(
            route = Screen.Search.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) }
        ) {
            SearchScreen(
                container = container,
                onResultClick = { type, id ->
                    when (type) {
                        "zone"  -> navController.navigate(Screen.ZoneDetail.createRoute(id))
                        "race"  -> navController.navigate(Screen.RaceDetail.createRoute(id))
                        "class" -> navController.navigate(Screen.ClassDetail.createRoute(id))
                        "quest" -> navController.navigate(Screen.QuestDetail.createRoute(id))
                        "npc"   -> navController.navigate(Screen.NpcDetail.createRoute(id))
                    }
                }
            )
        }

        // ── Favoritos ──────────────────────────────────────────────────────
        composable(
            route = Screen.Favorites.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) }
        ) {
            FavoritesScreen(
                container = container,
                onItemClick = { type, refId ->
                    when (type) {
                        "ZONE"  -> navController.navigate(Screen.ZoneDetail.createRoute(refId))
                        "RACE"  -> navController.navigate(Screen.RaceDetail.createRoute(refId))
                        "CLASS" -> navController.navigate(Screen.ClassDetail.createRoute(refId))
                        "QUEST" -> navController.navigate(Screen.QuestDetail.createRoute(refId))
                        "NPC"   -> navController.navigate(Screen.NpcDetail.createRoute(refId))
                    }
                }
            )
        }

        // ── Ajustes ────────────────────────────────────────────────────────
        composable(
            route = Screen.Settings.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) }
        ) {
            SettingsScreen(container = container)
        }
    }
}
