package com.example.turtlewowcompanion.ui.navigation

/** Rutas de navegación de la app. */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")

    // Zonas — navegación jerárquica
    data object ZoneList : Screen("zones")
    data object ZoneCities : Screen("zones/cities")
    data object ZoneDungeons : Screen("zones/dungeons")
    data object ZoneOpenWorld : Screen("zones/openworld")
    data object ZoneDetail : Screen("zones/{id}") {
        fun createRoute(id: Long) = "zones/$id"
    }

    // Bosses de mazmorra
    data object BossList : Screen("zones/{zoneId}/bosses") {
        fun createRoute(zoneId: Long) = "zones/$zoneId/bosses"
    }
    data object BossDetail : Screen("bosses/{id}") {
        fun createRoute(id: Long) = "bosses/$id"
    }

    data object RaceList : Screen("races")
    data object RaceDetail : Screen("races/{id}") {
        fun createRoute(id: Long) = "races/$id"
    }

    data object ClassList : Screen("classes")
    data object ClassDetail : Screen("classes/{id}") {
        fun createRoute(id: Long) = "classes/$id"
    }

    // Rutas legacy (mantenidas para favoritos/búsqueda)
    data object QuestList : Screen("quests")
    data object QuestDetail : Screen("quests/{id}") {
        fun createRoute(id: Long) = "quests/$id"
    }

    data object NpcList : Screen("npcs")
    data object NpcDetail : Screen("npcs/{id}") {
        fun createRoute(id: Long) = "npcs/$id"
    }

    data object Auth : Screen("auth")
    data object Profile : Screen("profile")
    data object Search : Screen("search")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")

    // Profesiones
    data object ProfessionList : Screen("professions")
    data object ProfessionDetail : Screen("professions/{id}") {
        fun createRoute(id: Long) = "professions/$id"
    }

    // Items (objetos)
    data object ItemList : Screen("items")
    data object ItemDetail : Screen("items/{id}") {
        fun createRoute(id: Long) = "items/$id"
    }
}
