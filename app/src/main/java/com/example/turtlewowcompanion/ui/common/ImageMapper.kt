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
            // Zonas con imagen incluida
            lower.contains("elwynn") -> R.drawable.img_zone_elwynn
            lower.contains("westfall") || lower.contains("páramos") || lower.contains("comarca del oeste") -> R.drawable.img_zone_westfall
            lower.contains("duskwood") || lower.contains("bosque del ocaso") -> R.drawable.img_zone_duskwood
            lower.contains("stranglethorn") || lower.contains("vega de tuercespina") -> R.drawable.img_zone_stranglethorn
            lower.contains("barrens") || lower.contains("baldío") -> R.drawable.img_zone_barrens
            lower.contains("durotar") -> R.drawable.img_zone_durotar
            lower.contains("ashenvale") || lower.contains("vallefresno") -> R.drawable.img_zone_ashenvale
            lower.contains("tanaris") -> R.drawable.img_zone_tanaris
            lower.contains("winterspring") || lower.contains("cuna del invierno") -> R.drawable.img_zone_winterspring
            lower.contains("plaguelands") || lower.contains("plague") || lower.contains("peste") -> R.drawable.img_zone_plaguelands

            // Zonas — Reinos del Este
            lower.contains("redridge") || lower.contains("crestagrana") -> R.drawable.img_zone_redridge
            lower.contains("loch modan") || lower.contains("loch") -> R.drawable.img_zone_lochmodan
            lower.contains("wetlands") || lower.contains("encharcad") || lower.contains("abnegad") || lower.contains("anegad") -> R.drawable.img_zone_wetlands
            lower.contains("arathi") -> R.drawable.img_zone_arathi
            lower.contains("hillsbrad") || lower.contains("trabalomas") -> R.drawable.img_zone_hillsbrad
            lower.contains("alterac") -> R.drawable.img_zone_alterac
            lower.contains("silverpine") || lower.contains("argént") -> R.drawable.img_zone_silverpine
            lower.contains("tirisfal") -> R.drawable.img_zone_tirisfal
            lower.contains("hinterlands") || lower.contains("tierras del interior") -> R.drawable.img_zone_hinterlands
            lower.contains("badlands") || lower.contains("inhóspita") -> R.drawable.img_zone_badlands
            lower.contains("searing") || lower.contains("garganta de fuego") -> R.drawable.img_zone_searinggorge
            lower.contains("burning") || lower.contains("estepas ardientes") -> R.drawable.img_zone_burningsteppes
            lower.contains("swamp") || lower.contains("sorrows") || lower.contains("pantano de las penas") -> R.drawable.img_zone_swampofsorrows
            lower.contains("blasted") || lower.contains("devastad") -> R.drawable.img_zone_blastedlands
            lower.contains("deadwind") || lower.contains("paso de la muerte") -> R.drawable.img_zone_deadwindpass
            lower.contains("dun morogh") || lower.contains("dun morog") -> optionalRes("img_zone_dunmorogh")

            // Zonas — Kalimdor
            lower.contains("darkshore") || lower.contains("costa oscura") -> R.drawable.img_zone_darkshore
            lower.contains("teldrassil") -> R.drawable.img_zone_teldrassil
            lower.contains("stonetalon") || lower.contains("espolón") -> R.drawable.img_zone_stonetalon
            lower.contains("desolace") -> R.drawable.img_zone_desolace
            lower.contains("feralas") -> R.drawable.img_zone_feralas
            lower.contains("thousand") || lower.contains("mil agujas") -> R.drawable.img_zone_thousandneedles
            lower.contains("un'goro") || lower.contains("ungoro") -> R.drawable.img_zone_ungoro
            lower.contains("silithus") -> R.drawable.img_zone_silithus
            lower.contains("felwood") || lower.contains("frondavil") -> R.drawable.img_zone_felwood
            lower.contains("azshara") -> R.drawable.img_zone_azshara
            lower.contains("moonglade") || lower.contains("claro de la luna") -> R.drawable.img_zone_moonglade
            lower.contains("mulgore") -> R.drawable.img_zone_mulgore
            lower.contains("dustwallow") || lower.contains("revolcafango") -> R.drawable.img_zone_dustwallow

            // Ciudades
            lower.contains("stormwind") || lower.contains("ventormenta") || lower.contains("ciudad de ventormenta") -> R.drawable.img_zone_stormwind
            lower.contains("ironforge") || lower.contains("forjaz") -> R.drawable.img_zone_ironforge
            lower.contains("darnassus") -> R.drawable.img_zone_darnassus
            lower.contains("orgrimmar") -> R.drawable.img_zone_orgrimmar
            lower.contains("thunder bluff") || lower.contains("cima del trueno") || lower.contains("thunderbluff") -> R.drawable.img_zone_thunderbluff
            lower.contains("undercity") || lower.contains("entrañas") || lower.contains("infraciudad") -> R.drawable.img_zone_undercity

            // Mazmorras — Reinos del Este
            lower.contains("deadmines") || lower.contains("minas de la muerte") -> R.drawable.img_zone_deadmines
            lower.contains("stockade") || lower.contains("las mazmorras") -> R.drawable.img_zone_stockade
            lower.contains("gnomeregan") -> R.drawable.img_zone_gnomeregan
            lower.contains("uldaman") -> R.drawable.img_zone_uldaman
            lower.contains("scholomance") -> R.drawable.img_zone_scholomance
            lower.contains("stratholme") -> R.drawable.img_zone_stratholme
            lower.contains("profundidades de roca negra") || lower.contains("blackrock depths") -> R.drawable.img_zone_brd
            lower.contains("cumbre de roca negra superior") || lower.contains("upper blackrock") -> R.drawable.img_zone_ubrs
            lower.contains("cumbre de roca negra inferior") || lower.contains("lower blackrock") || lower.contains("bajo roca negra") -> R.drawable.img_zone_lbrs
            lower.contains("sunken temple") || lower.contains("templo sumergido") -> R.drawable.img_zone_sunkentemple
            lower.contains("shadowfang") || lower.contains("colmillo oscuro") -> R.drawable.img_zone_sfk
            lower.contains("scarlet monastery") || lower.contains("monasterio escarlata") -> R.drawable.img_zone_sm
            lower.contains("razorfen downs") || lower.contains("zahúrda") || lower.contains("zahrda") || lower.contains("zahurda") -> R.drawable.img_zone_razorfendowns
            lower.contains("razorfen kraul") || lower.contains("hoyo rajacieno") || lower.contains("horado rajacieno") -> R.drawable.img_zone_razorfenkraul

            // Mazmorras — Kalimdor
            lower.contains("ragefire") || lower.contains("sima ígnea") || lower.contains("sima ignea") || lower.contains("cavernas ígneas") || lower.contains("cavernas igneas") -> R.drawable.img_zone_rfc
            lower.contains("wailing") || lower.contains("lamentos") -> R.drawable.img_zone_wailingcaverns
            lower.contains("blackfathom") || lower.contains("brazanegra") || lower.contains("sima negra") -> R.drawable.img_zone_bfd
            lower.contains("maraudon") -> R.drawable.img_zone_maraudon
            lower.contains("zul'farrak") || lower.contains("zulfarrak") -> R.drawable.img_zone_zulfarrak
            lower.contains("dire maul") || lower.contains("masacre") -> R.drawable.img_zone_diremaul

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
            lowerName.contains("hakkar") -> optionalRes("img_npc_hakkar")
            lowerName.contains("c'thun") || lowerName.contains("cthun") -> optionalRes("img_npc_cthun")
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

    fun bossImageRes(bossName: String): Int? {
        val lower = bossName.lowercase()
        return when {
            // Minas de la Muerte
            lower.contains("rhahk") -> optionalRes("img_boss_rhahkzor")
            lower.contains("sneed") -> optionalRes("img_boss_sneed")
            lower.contains("gilnid") -> optionalRes("img_boss_gilnid")
            lower.contains("smite") || lower.contains("machacador") -> optionalRes("img_boss_mrbiggle")
            lower.contains("vancleef") || lower.contains("edwin") -> optionalRes("img_boss_vancleef")
            lower.contains("cookie") || lower.contains("cocinitas") -> optionalRes("img_boss_cookie")
            // Cuevas de los Lamentos
            lower.contains("cobrahn") -> optionalRes("img_boss_lordcobrahn")
            lower.contains("pythas") -> optionalRes("img_boss_lordpythas")
            lower.contains("serpentis") -> optionalRes("img_boss_lordserpentis")
            lower.contains("skum") -> optionalRes("img_boss_skum")
            lower.contains("mutanus") -> optionalRes("img_boss_mutanus")
            lower.contains("verdan") -> optionalRes("img_boss_verdan")
            // Sima Ígnea
            lower.contains("oggleflint") -> optionalRes("img_boss_oggleflint")
            lower.contains("taragaman") -> optionalRes("img_boss_taragaman")
            lower.contains("jergosh") -> optionalRes("img_boss_jergosh")
            lower.contains("bazzalan") -> optionalRes("img_boss_bazzalan")
            // Colmillo Oscuro
            lower.contains("razorclaw") -> optionalRes("img_boss_razorclaw")
            lower.contains("silverlaine") -> optionalRes("img_boss_baronsilver")
            lower.contains("nandos") -> optionalRes("img_boss_wolfmaster")
            lower.contains("arugal") -> optionalRes("img_boss_arugal")
            // Stockade
            lower.contains("bazil") -> optionalRes("img_boss_bazilthredd")
            lower.contains("dextren") -> optionalRes("img_boss_dextrenward")
            lower.contains("targorr") -> optionalRes("img_boss_targorr")
            // Gnomeregan
            lower.contains("grubbis") -> optionalRes("img_boss_grubbis")
            lower.contains("viscous") -> optionalRes("img_boss_viscousfallout")
            lower.contains("electrocutor") || lower.contains("electrocutioner") -> optionalRes("img_boss_electrocutioner")
            lower.contains("crowd") || lower.contains("aplasta") || lower.contains("pummeler") -> optionalRes("img_boss_crowdpummeler")
            lower.contains("thermaplugg") -> optionalRes("img_boss_thermaplugg")
            // Monasterio Escarlata
            lower.contains("vishas") -> optionalRes("img_boss_interrogator")
            lower.contains("thalnos") -> optionalRes("img_boss_bloodmage")
            lower.contains("loksey") -> optionalRes("img_boss_houndmaster")
            lower.contains("herod") -> optionalRes("img_boss_herod")
            lower.contains("mograine") -> optionalRes("img_boss_mograine")
            lower.contains("whitemane") || lower.contains("melenablanca") -> optionalRes("img_boss_whitemane")
            // Rajacieno
            lower.contains("aggem") -> optionalRes("img_boss_aggemthorr")
            lower.contains("charlga") -> optionalRes("img_boss_charlga")
            lower.contains("mordresh") -> optionalRes("img_boss_mordresh")
            lower.contains("amnennar") -> optionalRes("img_boss_amnennar")
            // Uldaman
            lower.contains("revelosh") -> optionalRes("img_boss_revelosh")
            lower.contains("ironaya") -> optionalRes("img_boss_ironaya")
            lower.contains("archaedas") -> optionalRes("img_boss_archaedas")
            // Zul'Farrak
            lower.contains("antu'sul") || lower.contains("antusul") -> optionalRes("img_boss_antusul")
            lower.contains("theka") -> optionalRes("img_boss_theka")
            lower.contains("ukorz") -> optionalRes("img_boss_chiefukorz")
            // Maraudon
            lower.contains("noxxion") -> optionalRes("img_boss_noxxion")
            lower.contains("razorlash") -> optionalRes("img_boss_razorlash")
            lower.contains("celebras") -> optionalRes("img_boss_celebras")
            lower.contains("theradras") -> optionalRes("img_boss_princesstheradin")
            // Templo Sumergido
            lower.contains("atal'alarion") || lower.contains("atalalarion") -> optionalRes("img_boss_atalalarion")
            lower.contains("jammal") -> optionalRes("img_boss_jamelanin")
            lower.contains("eranikus") -> optionalRes("img_boss_shadeoferanikus")
            // Profundidades de Roca Negra
            lower.contains("gerstahn") -> optionalRes("img_boss_highinterrogator")
            lower.contains("roccor") -> optionalRes("img_boss_lordroccor")
            lower.contains("thaurissan") || lower.contains("dagran") -> optionalRes("img_boss_emperordagran")
            lower.contains("moira") -> optionalRes("img_boss_princessmoira")
            // LBRS
            lower.contains("omokk") -> optionalRes("img_boss_highlordomokk")
            lower.contains("rend") -> optionalRes("img_boss_warchief")
            lower.contains("wyrmthalak") || lower.contains("vermithalak") -> optionalRes("img_boss_overlordwyrmthalak")
            // UBRS
            lower.contains("bestia") || lower.contains("beast") -> optionalRes("img_boss_thebeast")
            lower.contains("drakkisath") -> optionalRes("img_boss_generaldrakkisath")
            // Stratholme
            lower.contains("rivendare") -> optionalRes("img_boss_baronrivendare")
            lower.contains("barthilas") -> optionalRes("img_boss_magistrate")
            lower.contains("balnazzar") -> optionalRes("img_boss_balnazzar")
            // Scholomance
            lower.contains("rattlegore") || lower.contains("traquesangre") -> optionalRes("img_boss_rattlegore")
            lower.contains("gandling") -> optionalRes("img_boss_darkmaster")
            lower.contains("ras") && (lower.contains("frost") || lower.contains("murmuhielo")) -> optionalRes("img_boss_rasfrost")
            // Dire Maul
            lower.contains("zevrim") -> optionalRes("img_boss_zevrim")
            lower.contains("alzzin") -> optionalRes("img_boss_alzzin")
            lower.contains("gordok") -> optionalRes("img_boss_kinggordok")
            lower.contains("immol'thar") || lower.contains("immolthar") -> optionalRes("img_boss_immolthar")
            lower.contains("tortheldrin") -> optionalRes("img_boss_princetortheldrin")
            // Brazanegra
            lower.contains("gelihast") -> optionalRes("img_boss_gelihast")
            lower.contains("aku'mai") || lower.contains("akumai") -> optionalRes("img_boss_akulagarr")
            lower.contains("kelris") -> optionalRes("img_boss_twilightlord")
            // Genérico
            else -> null
        }
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
