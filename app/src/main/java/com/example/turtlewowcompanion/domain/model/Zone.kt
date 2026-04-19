package com.example.turtlewowcompanion.domain.model

data class Zone(
    val id: Long,
    val name: String,
    val description: String,
    val minLevel: Int,
    val maxLevel: Int,
    val continent: String,
    val zoneType: String,
    val factionName: String?
) {
    /** Rango de nivel legible, e.g. "17-26" o "60" */
    val level: String get() = if (minLevel == maxLevel) "$minLevel" else "$minLevel-$maxLevel"
    /** Alias para compatibilidad con FactionBadge y FactionThemeProvider */
    val faction: String get() = factionName ?: "Neutral"

    /** Etiqueta legible del tipo de zona */
    val zoneTypeLabel: String get() = when (zoneType) {
        "DUNGEON"     -> "Mazmorra"
        "RAID"        -> "Banda"
        "CITY"        -> "Ciudad"
        "BATTLEGROUND"-> "Campo de batalla"
        "CONTESTED"   -> "En disputa"
        else          -> "Mundo abierto"
    }

    /** Etiqueta legible del continente */
    val continentLabel: String get() = when (continent) {
        "EASTERN_KINGDOMS" -> "Reinos del Este"
        "KALIMDOR"         -> "Kalimdor"
        else               -> continent
    }
}
