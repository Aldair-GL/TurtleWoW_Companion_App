package com.example.turtlewowcompanion.core

enum class GuideCategory(val label: String, val apiPath: String) {
    OBJECTS("Objetos", "objetos"),
    LORE("Lore", "lore"),
    MAPS("Mapas", "mapas"),
    CLASSES("Clases", "clases")
}

data class GuideSeedEntry(
    val title: String,
    val description: String
)

object GuideSeedData {
    fun seedFor(category: GuideCategory): List<GuideSeedEntry> = when (category) {
        GuideCategory.OBJECTS -> listOf(
            GuideSeedEntry("Bolsa de Viajero", "Amplía inventario al inicio de aventura."),
            GuideSeedEntry("Piedra de hogar", "Permite volver a una posada vinculada."),
            GuideSeedEntry("Botas de rapidez", "Mejoran la movilidad en rutas largas.")
        )
        GuideCategory.LORE -> listOf(
            GuideSeedEntry("Historia de Turtle WoW", "Servidor centrado en experiencia clásica con contenido nuevo."),
            GuideSeedEntry("Nuevas zonas", "Expansión de zonas y cadenas de misiones en Azeroth."),
            GuideSeedEntry("Rol y comunidad", "Enfoque fuerte en comunidad y progresión orgánica.")
        )
        GuideCategory.MAPS -> listOf(
            GuideSeedEntry("Bosque de Elwynn", "Zona inicial de la Alianza con rutas de farmeo seguras."),
            GuideSeedEntry("Los Baldíos", "Amplia zona de la Horda con quests encadenadas."),
            GuideSeedEntry("Vega de Tuercespina", "Área mixta preparada para PvP y exploración.")
        )
        GuideCategory.CLASSES -> listOf(
            GuideSeedEntry("Guerrero", "Tanque/melee versátil, gran escalado con equipo."),
            GuideSeedEntry("Mago", "DPS de rango con control de masas y daño explosivo."),
            GuideSeedEntry("Druida", "Clase híbrida de soporte, tanque, melee o caster.")
        )
    }
}
