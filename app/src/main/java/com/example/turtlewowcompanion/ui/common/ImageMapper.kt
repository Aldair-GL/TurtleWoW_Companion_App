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
            lower.contains("elwynn") || lower.contains("bosque de elwynn") -> R.drawable.img_zone_elwynn
            lower.contains("westfall") || lower.contains("comarca del oeste") || lower.contains("páramos de poniente") -> R.drawable.img_zone_westfall
            lower.contains("duskwood") || lower.contains("bosque del ocaso") -> R.drawable.img_zone_duskwood
            lower.contains("stranglethorn") || lower.contains("vega de tuercespina") -> R.drawable.img_zone_stranglethorn
            lower.contains("barrens") || lower.contains("baldío") || lower.contains("los baldíos") || lower.contains("los baldios") -> R.drawable.img_zone_barrens
            lower.contains("durotar") -> R.drawable.img_zone_durotar
            lower.contains("ashenvale") || lower.contains("vallefresno") -> R.drawable.img_zone_ashenvale
            lower.contains("tanaris") -> R.drawable.img_zone_tanaris
            lower.contains("winterspring") || lower.contains("cuna del invierno") -> R.drawable.img_zone_winterspring
            lower.contains("plaguelands") || lower.contains("plague") || lower.contains("tierras de la peste") -> R.drawable.img_zone_plaguelands
            lower.contains("redridge") || lower.contains("crestagrana") -> optionalRes("img_zone_redridge")
            lower.contains("arathi") -> optionalRes("img_zone_arathi")
            lower.contains("hillsbrad") || lower.contains("trabalomas") -> optionalRes("img_zone_hillsbrad")
            lower.contains("alterac") -> optionalRes("img_zone_alterac")
            lower.contains("wetlands") || lower.contains("tierras anegadas") || lower.contains("anegadas") -> optionalRes("img_zone_wetlands")
            lower.contains("tirisfal") || lower.contains("claros de tirisfal") -> optionalRes("img_zone_tirisfal")
            lower.contains("hinterlands") || lower.contains("tierras del interior") -> optionalRes("img_zone_hinterlands")
            lower.contains("silverpine") || lower.contains("bosque de argent") || lower.contains("argentos") || lower.contains("argenteo") -> optionalRes("img_zone_silverpine")
            lower.contains("burning") || lower.contains("estepas ardientes") -> optionalRes("img_zone_burningsteppes")
            lower.contains("darkshore") || lower.contains("costa oscura") -> optionalRes("img_zone_darkshore")
            lower.contains("silithus") -> optionalRes("img_zone_silithus")
            lower.contains("felwood") || lower.contains("frondavil") -> optionalRes("img_zone_felwood")
            lower.contains("azshara") -> optionalRes("img_zone_azshara")
            lower.contains("moonglade") || lower.contains("claro de la luna") -> optionalRes("img_zone_moonglade")
            lower.contains("mulgore") -> optionalRes("img_zone_mulgore")
            lower.contains("dustwallow") || lower.contains("revolcafango") || lower.contains("marisma de dustwallow") -> optionalRes("img_zone_dustwallow")
            lower.contains("dun morogh") || lower.contains("dunmorogh") -> optionalRes("img_zone_dunmorogh")
            lower.contains("teldrassil") -> optionalRes("img_zone_teldrassil")
            lower.contains("loch modan") -> optionalRes("img_zone_lochmodan")
            lower.contains("desolace") || lower.contains("desolación") -> optionalRes("img_zone_desolace")
            lower.contains("feralas") -> optionalRes("img_zone_feralas")
            lower.contains("un'goro") || lower.contains("ungoro") || lower.contains("cráter de un'goro") -> optionalRes("img_zone_ungoro")
            lower.contains("blasted") || lower.contains("tierras devastadas") -> optionalRes("img_zone_blastedlands")
            lower.contains("swamp of sorrows") || lower.contains("pantano de las penas") -> optionalRes("img_zone_swampofsorrows")
            lower.contains("deadwind") || lower.contains("paso de la muerte") -> optionalRes("img_zone_deadwindpass")
            lower.contains("stonetalon") || lower.contains("sierra del espolón") || lower.contains("espolon") || lower.contains("espolón") -> optionalRes("img_zone_stonetalon")
            lower.contains("thousand") || lower.contains("mil agujas") -> optionalRes("img_zone_thousandneedles")
            lower.contains("badlands") || lower.contains("tierras inhóspitas") -> optionalRes("img_zone_badlands")
            lower.contains("searing") || lower.contains("garganta de fuego") -> optionalRes("img_zone_searinggorge")

            // Ciudades
            lower.contains("stormwind") || lower.contains("ventormenta") -> optionalRes("img_zone_stormwind")
            lower.contains("ironforge") || lower.contains("forjaz") -> optionalRes("img_zone_ironforge")
            lower.contains("darnassus") -> optionalRes("img_zone_darnassus")
            lower.contains("orgrimmar") -> optionalRes("img_zone_orgrimmar")
            lower.contains("thunder bluff") || lower.contains("cima del trueno") -> optionalRes("img_zone_thunderbluff")
            lower.contains("undercity") || lower.contains("entrañas") || lower.contains("infraciudad") -> optionalRes("img_zone_undercity")

            // Mazmorras — Reinos del Este
            lower.contains("deadmines") || lower.contains("minas de la muerte") -> optionalRes("img_zone_deadmines")
            lower.contains("gnomeregan") -> optionalRes("img_zone_gnomeregan")
            lower.contains("uldaman") -> optionalRes("img_zone_uldaman")
            lower.contains("scholomance") -> optionalRes("img_zone_scholomance")
            lower.contains("stratholme") -> optionalRes("img_zone_stratholme")
            lower.contains("stockade") || lower.contains("mazmorras") -> optionalRes("img_zone_stockade")
            lower.contains("shadowfang") || lower.contains("colmillo oscuro") -> optionalRes("img_zone_sfk")
            lower.contains("scarlet") || lower.contains("monasterio escarlata") -> optionalRes("img_zone_sm")
            lower.contains("blackrock depth") || lower.contains("profundidades de roca negra") || lower.contains("brd") -> optionalRes("img_zone_brd")
            lower.contains("lower blackrock") || lower.contains("lbrs") || lower.contains("cumbre de roca negra inferior") -> optionalRes("img_zone_lbrs")
            lower.contains("upper blackrock") || lower.contains("ubrs") || lower.contains("cumbre de roca negra superior") -> optionalRes("img_zone_ubrs")

            // Mazmorras — Kalimdor
            lower.contains("wailing") || lower.contains("lamentos") || lower.contains("cuevas de los lamentos") -> optionalRes("img_zone_wailingcaverns")
            lower.contains("ragefire") || lower.contains("sima ígnea") || lower.contains("sima ignea") || lower.contains("caverna ígnea") || lower.contains("caverna ignea") || lower.contains("cavernas") || lower.contains("gnea") -> optionalRes("img_zone_rfc")
            lower.contains("razorfen kraul") || lower.contains("horado rajacieno") || lower.contains("horado") || lower.contains("rajacieno kraul") -> optionalRes("img_zone_razorfenkraul")
            lower.contains("razorfen downs") || lower.contains("zahurda rajacieno") || lower.contains("zahurda") || lower.contains("rajacieno downs") -> optionalRes("img_zone_razorfendowns")
            lower.contains("maraudon") -> optionalRes("img_zone_maraudon")
            lower.contains("dire maul") || lower.contains("la masacre") -> optionalRes("img_zone_diremaul")
            lower.contains("zul'farrak") || lower.contains("zulfarrak") || lower.contains("zul") -> optionalRes("img_zone_zulfarrak")
            lower.contains("sunken") || lower.contains("templo sumergido") -> optionalRes("img_zone_sunkentemple")
            lower.contains("blackfathom") || lower.contains("brazanegra") || lower.contains("caverna de brazanegra") -> optionalRes("img_zone_bfd")

            else -> null
        }
    }

    fun npcBrush(npcType: String): Brush {
        val lower = npcType.lowercase()
        return when {
            lower.contains("boss") || lower.contains("elite") -> ThemeBrushes.npcs
            lower.contains("vendor") || lower.contains("merchant") -> ThemeBrushes.quests
            else -> ThemeBrushes.zones
        }
    }

    fun npcImageRes(npcName: String, npcType: String = ""): Int? {
        val lowerName = npcName.lowercase()
        val lowerType = npcType.lowercase()
        return when {
            lowerName.contains("sylvanas") -> optionalRes("img_npc_sylvanas")
            lowerName.contains("cairne") -> optionalRes("img_npc_cairne")
            lowerName.contains("tyrande") -> optionalRes("img_npc_tyrande")
            lowerName.contains("magni") -> optionalRes("img_npc_magni")
            lowerName.contains("nefarian") || lowerName.contains("nefarion") -> optionalRes("img_npc_nefarian")
            lowerName.contains("kel'thuzad") || lowerName.contains("kelthuzad") -> optionalRes("img_npc_kelthuzad")
            lowerName.contains("hakkar") -> optionalRes("img_npc_hakkar")
            lowerName.contains("c'thun") || lowerName.contains("cthun") -> optionalRes("img_npc_cthun")
            lowerName.contains("hogger") -> R.drawable.img_npc_hogger
            lowerType.contains("boss") || lowerType.contains("elite") || lowerType.contains("jefe") -> R.drawable.img_npc_boss
            lowerType.contains("vendor") || lowerType.contains("merchant") || lowerType.contains("vendedor") -> R.drawable.img_npc_vendor
            lowerType.contains("quest") || lowerType.contains("misión") -> R.drawable.img_npc_questgiver
            lowerType.contains("trainer") || lowerType.contains("train") || lowerType.contains("entrenador") -> R.drawable.img_npc_trainer
            lowerType.contains("flight") || lowerType.contains("gryphon") ||
                lowerType.contains("wind rider") || lowerType.contains("vuelo") -> R.drawable.img_npc_flightmaster
            lowerType.contains("innkeeper") || lowerType.contains("tabern") || lowerType.contains("posadero") -> optionalRes("img_npc_innkeeper")
            lowerType.contains("guard") || lowerType.contains("guardia") -> optionalRes("img_npc_guard")
            lowerType.contains("repair") || lowerType.contains("armorsmith") ||
                lowerType.contains("weaponsmith") || lowerType.contains("reparador") -> optionalRes("img_npc_repair")
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
