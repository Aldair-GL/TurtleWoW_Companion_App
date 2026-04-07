package com.example.turtlewowcompanion.ui.common

import androidx.compose.ui.graphics.Brush

/**
 * Mapea nombres de zonas, tipos de NPC, etc. a pinceles de fondo temáticos.
 */
object ImageMapper {

    fun zoneBrush(zoneName: String): Brush {
        val lower = zoneName.lowercase()
        return when {
            lower.contains("forest") || lower.contains("elwynn") || lower.contains("ashenvale") ||
                lower.contains("teldrassil") || lower.contains("darkshore") -> ThemeBrushes.zones
            lower.contains("barrens") || lower.contains("desert") || lower.contains("tanaris") ||
                lower.contains("silithus") || lower.contains("desolace") -> ThemeBrushes.quests
            lower.contains("mountain") || lower.contains("peak") || lower.contains("dun") ||
                lower.contains("ironforge") || lower.contains("alterac") -> ThemeBrushes.npcs
            else -> ThemeBrushes.zones
        }
    }

    fun npcBrush(npcType: String): Brush {
        val lower = npcType.lowercase()
        return when {
            lower.contains("boss") || lower.contains("elite") -> ThemeBrushes.npcs
            lower.contains("vendor") || lower.contains("merchant") -> ThemeBrushes.quests
            lower.contains("quest") -> ThemeBrushes.zones
            else -> ThemeBrushes.npcs
        }
    }

    fun questBrush(zone: String): Brush {
        return zoneBrush(zone)
    }
}
