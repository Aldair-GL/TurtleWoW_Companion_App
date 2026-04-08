package com.example.turtlewowcompanion.ui.common

import androidx.compose.ui.graphics.Brush
import com.example.turtlewowcompanion.R

/**
 * Mapea nombres de zonas, tipos de NPC, etc. a pinceles de fondo
 * y a recursos drawable cuando hay imagen disponible.
 */
object ImageMapper {

    fun zoneBrush(zoneName: String): Brush {
        val lower = zoneName.lowercase()
        return when {
            lower.contains("forest") || lower.contains("elwynn") || lower.contains("ashenvale") ||
                lower.contains("teldrassil") || lower.contains("darkshore") ||
                lower.contains("feralas") || lower.contains("hinterlands") -> ThemeBrushes.zones
            lower.contains("barrens") || lower.contains("desert") || lower.contains("tanaris") ||
                lower.contains("silithus") || lower.contains("desolace") ||
                lower.contains("thousand") || lower.contains("badlands") -> ThemeBrushes.quests
            lower.contains("mountain") || lower.contains("peak") || lower.contains("dun") ||
                lower.contains("ironforge") || lower.contains("alterac") ||
                lower.contains("burning") || lower.contains("searing") -> ThemeBrushes.npcs
            else -> ThemeBrushes.zones
        }
    }

    fun zoneImageRes(zoneName: String): Int? {
        val lower = zoneName.lowercase()
        return when {
            lower.contains("elwynn") -> R.drawable.img_zone_elwynn
            lower.contains("westfall") -> R.drawable.img_zone_westfall
            lower.contains("duskwood") -> R.drawable.img_zone_duskwood
            lower.contains("stranglethorn") -> R.drawable.img_zone_stranglethorn
            lower.contains("barrens") -> R.drawable.img_zone_barrens
            lower.contains("durotar") -> R.drawable.img_zone_durotar
            lower.contains("ashenvale") -> R.drawable.img_zone_ashenvale
            lower.contains("tanaris") -> R.drawable.img_zone_tanaris
            lower.contains("winterspring") -> R.drawable.img_zone_winterspring
            lower.contains("plaguelands") || lower.contains("plague") -> R.drawable.img_zone_plaguelands
            lower.contains("redridge") -> optionalRes("img_zone_redridge")
            lower.contains("loch modan") || lower.contains("loch") -> optionalRes("img_zone_lochmodan")
            lower.contains("wetlands") -> optionalRes("img_zone_wetlands")
            lower.contains("arathi") -> optionalRes("img_zone_arathi")
            lower.contains("hillsbrad") -> optionalRes("img_zone_hillsbrad")
            lower.contains("alterac") -> optionalRes("img_zone_alterac")
            lower.contains("silverpine") -> optionalRes("img_zone_silverpine")
            lower.contains("tirisfal") -> optionalRes("img_zone_tirisfal")
            lower.contains("darkshore") -> optionalRes("img_zone_darkshore")
            lower.contains("teldrassil") -> optionalRes("img_zone_teldrassil")
            lower.contains("stonetalon") -> optionalRes("img_zone_stonetalon")
            lower.contains("desolace") -> optionalRes("img_zone_desolace")
            lower.contains("feralas") -> optionalRes("img_zone_feralas")
            lower.contains("thousand") -> optionalRes("img_zone_thousandneedles")
            lower.contains("un'goro") || lower.contains("ungoro") -> optionalRes("img_zone_ungoro")
            lower.contains("silithus") -> optionalRes("img_zone_silithus")
            lower.contains("felwood") -> optionalRes("img_zone_felwood")
            lower.contains("azshara") -> optionalRes("img_zone_azshara")
            lower.contains("moonglade") -> optionalRes("img_zone_moonglade")
            lower.contains("badlands") -> optionalRes("img_zone_badlands")
            lower.contains("searing") -> optionalRes("img_zone_searinggorge")
            lower.contains("burning") -> optionalRes("img_zone_burningsteppes")
            lower.contains("swamp") || lower.contains("sorrows") -> optionalRes("img_zone_swampofsorrows")
            lower.contains("blasted") -> optionalRes("img_zone_blastedlands")
            lower.contains("deadwind") -> optionalRes("img_zone_deadwindpass")
            lower.contains("hinterlands") -> optionalRes("img_zone_hinterlands")
            lower.contains("mulgore") -> optionalRes("img_zone_mulgore")
            else -> null
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

    fun npcImageRes(npcName: String, npcType: String): Int? {
        val lowerName = npcName.lowercase()
        val lowerType = npcType.lowercase()
        return when {
            lowerName.contains("hogger") -> R.drawable.img_npc_hogger
            lowerName.contains("edwin") || lowerName.contains("vancleef") -> optionalRes("img_npc_vancleef")
            lowerName.contains("ragnaros") -> optionalRes("img_npc_ragnaros")
            lowerName.contains("onyxia") -> optionalRes("img_npc_onyxia")
            lowerName.contains("thrall") -> optionalRes("img_npc_thrall")
            lowerName.contains("jaina") -> optionalRes("img_npc_jaina")
            lowerName.contains("bolvar") -> optionalRes("img_npc_bolvar")
            lowerName.contains("sylvanas") -> optionalRes("img_npc_sylvanas")
            lowerName.contains("cairne") -> optionalRes("img_npc_cairne")
            lowerName.contains("tyrande") -> optionalRes("img_npc_tyrande")
            lowerName.contains("magni") -> optionalRes("img_npc_magni")
            lowerName.contains("nefarian") || lowerName.contains("nefarion") -> optionalRes("img_npc_nefarian")
            lowerName.contains("kel'thuzad") || lowerName.contains("kelthuzad") -> optionalRes("img_npc_kelthuzad")
            lowerName.contains("baine") -> optionalRes("img_npc_baine")
            lowerType.contains("boss") || lowerType.contains("elite") -> R.drawable.img_npc_boss
            lowerType.contains("vendor") || lowerType.contains("merchant") -> R.drawable.img_npc_vendor
            lowerType.contains("quest") -> R.drawable.img_npc_questgiver
            lowerType.contains("trainer") || lowerType.contains("train") -> R.drawable.img_npc_trainer
            lowerType.contains("flight") || lowerType.contains("gryphon") ||
                lowerType.contains("wind rider") -> R.drawable.img_npc_flightmaster
            lowerType.contains("innkeeper") || lowerType.contains("tabern") -> optionalRes("img_npc_innkeeper")
            lowerType.contains("guard") || lowerType.contains("guardia") -> optionalRes("img_npc_guard")
            lowerType.contains("repair") || lowerType.contains("armorsmith") ||
                lowerType.contains("weaponsmith") -> optionalRes("img_npc_repair")
            else -> null
        }
    }

    fun questBrush(zone: String): Brush {
        return zoneBrush(zone)
    }

    fun questImageRes(zone: String): Int? {
        return zoneImageRes(zone)
    }

    /**
     * Intenta cargar un recurso por nombre de forma segura.
     * Si no existe el drawable, devuelve null en vez de crashear.
     */
    private fun optionalRes(name: String): Int? {
        return try {
            val field = R.drawable::class.java.getDeclaredField(name)
            field.getInt(null)
        } catch (_: Exception) {
            null
        }
    }
}
