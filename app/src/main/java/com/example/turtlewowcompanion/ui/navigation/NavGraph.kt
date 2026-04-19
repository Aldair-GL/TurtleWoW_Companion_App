package com.example.turtlewowcompanion.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.screens.bosses.BossDetailScreen
import com.example.turtlewowcompanion.ui.screens.bosses.BossListScreen
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
import com.example.turtlewowcompanion.ui.screens.zones.ZoneCategoryScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneDetailScreen
import com.example.turtlewowcompanion.ui.screens.zones.ZoneListScreen

private const val ANIM = 350

@Composable
fun NavGraph(
    navController: NavHostController,
    container: AppContainer,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = { fadeIn(tween(ANIM)) },
        exitTransition = { fadeOut(tween(ANIM)) }
    ) {
        // ── Home ───────────────────────────────────────────────────────────
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToZones = { navController.navigate(Screen.ZoneList.route) },
                onNavigateToRaces = { navController.navigate(Screen.RaceList.route) },
                onNavigateToClasses = { navController.navigate(Screen.ClassList.route) }
            )
        }

        // ── Zonas: menú jerárquico ─────────────────────────────────────────
        composable(
            route = Screen.ZoneList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            ZoneListScreen(
                onNavigateToCities = { navController.navigate(Screen.ZoneCities.route) },
                onNavigateToDungeons = { navController.navigate(Screen.ZoneDungeons.route) },
                onNavigateToOpenWorld = { navController.navigate(Screen.ZoneOpenWorld.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Mundo abierto
        composable(
            route = Screen.ZoneOpenWorld.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            ZoneCategoryScreen(
                container = container, zoneType = "OPEN_WORLD", title = "Mundo abierto",
                onZoneClick = { navController.navigate(Screen.ZoneDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }

        // Ciudades
        composable(
            route = Screen.ZoneCities.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            ZoneCategoryScreen(
                container = container, zoneType = "CITY", title = "Ciudades",
                onZoneClick = { navController.navigate(Screen.ZoneDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }

        // Mazmorras
        composable(
            route = Screen.ZoneDungeons.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            ZoneCategoryScreen(
                container = container, zoneType = "DUNGEON", title = "Mazmorras",
                onZoneClick = { navController.navigate(Screen.ZoneDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }

        // Detalle de zona (con botón "Ver jefes" para mazmorras)
        composable(
            route = Screen.ZoneDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            ZoneDetailScreen(
                zoneId = id,
                container = container,
                onBack = { navController.popBackStack() },
                onNavigateToBosses = { zoneId ->
                    navController.navigate(Screen.BossList.createRoute(zoneId))
                }
            )
        }

        // ── Bosses ─────────────────────────────────────────────────────────
        composable(
            route = Screen.BossList.route,
            arguments = listOf(navArgument("zoneId") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) { entry ->
            val zoneId = entry.arguments?.getLong("zoneId") ?: return@composable
            BossListScreen(
                zoneId = zoneId,
                zoneName = "",
                container = container,
                onBossClick = { navController.navigate(Screen.BossDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.BossDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            BossDetailScreen(bossId = id, container = container, onBack = { navController.popBackStack() })
        }

        // ── Razas ──────────────────────────────────────────────────────────
        composable(
            route = Screen.RaceList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            RaceListScreen(
                container = container,
                onRaceClick = { navController.navigate(Screen.RaceDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.RaceDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            RaceDetailScreen(raceId = id, container = container, onBack = { navController.popBackStack() })
        }

        // ── Clases ─────────────────────────────────────────────────────────
        composable(
            route = Screen.ClassList.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) {
            ClassListScreen(
                container = container,
                onClassClick = { navController.navigate(Screen.ClassDetail.createRoute(it)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ClassDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM)) }
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            ClassDetailScreen(classId = id, container = container, onBack = { navController.popBackStack() })
        }

        // ── Legacy: Quests / NPCs ──────────────────────────────────────────
        composable(Screen.QuestList.route) {
            QuestListScreen(container = container, onQuestClick = {}, onBack = { navController.popBackStack() })
        }
        composable(Screen.QuestDetail.route, arguments = listOf(navArgument("id") { type = NavType.LongType })) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            QuestDetailScreen(questId = id, container = container, onBack = { navController.popBackStack() })
        }
        composable(Screen.NpcList.route) {
            NpcListScreen(container = container, onNpcClick = {}, onBack = { navController.popBackStack() })
        }
        composable(Screen.NpcDetail.route, arguments = listOf(navArgument("id") { type = NavType.LongType })) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            NpcDetailScreen(npcId = id, container = container, onBack = { navController.popBackStack() })
        }

        // ── Búsqueda / Favoritos / Ajustes ─────────────────────────────────
        composable(Screen.Search.route) {
            SearchScreen(container = container, onResultClick = { type, id ->
                when (type) {
                    "zone" -> navController.navigate(Screen.ZoneDetail.createRoute(id))
                    "race" -> navController.navigate(Screen.RaceDetail.createRoute(id))
                    "class" -> navController.navigate(Screen.ClassDetail.createRoute(id))
                    "quest" -> navController.navigate(Screen.QuestDetail.createRoute(id))
                    "npc" -> navController.navigate(Screen.NpcDetail.createRoute(id))
                }
            })
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(container = container, onItemClick = { type, refId ->
                when (type) {
                    "ZONE" -> navController.navigate(Screen.ZoneDetail.createRoute(refId))
                    "QUEST" -> navController.navigate(Screen.QuestDetail.createRoute(refId))
                    "NPC" -> navController.navigate(Screen.NpcDetail.createRoute(refId))
                }
            })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(container = container)
        }
    }
}
