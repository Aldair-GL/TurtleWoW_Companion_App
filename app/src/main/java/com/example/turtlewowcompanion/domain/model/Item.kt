package com.example.turtlewowcompanion.domain.model

data class Item(
    val id: Long,
    val name: String,
    val description: String,
    val quality: String,
    val type: String,
    val subtype: String,
    val levelRequired: Int?,
    val itemLevel: Int?,
    val professionName: String?
) {
    val qualityLabel: String get() = when (quality) {
        "POOR"      -> "Pobre"
        "COMMON"    -> "Común"
        "UNCOMMON"  -> "Poco común"
        "RARE"      -> "Raro"
        "EPIC"      -> "Épico"
        "LEGENDARY" -> "Legendario"
        else        -> quality
    }

    val typeLabel: String get() = when (type) {
        "WEAPON"    -> "Arma"
        "ARMOR"     -> "Armadura"
        "CONSUMABLE"-> "Consumible"
        "QUEST"     -> "Misión"
        "TRADE"     -> "Comercio"
        "REAGENT"   -> "Reagente"
        "RECIPE"    -> "Receta"
        "MISC"      -> "Misceláneo"
        else        -> type
    }
}

