package com.example.turtlewowcompanion.ui.navigation

/** Rutas de navegación de la app. */
sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object ZoneList : Screen("zones")
    data object ZoneDetail : Screen("zones/{id}") {
        fun createRoute(id: Long) = "zones/$id"
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

    data object Search : Screen("search")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")
}
