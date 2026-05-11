package com.example.turtlewowcompanion.domain.model

data class LootItem(
    val id: Long,
    val name: String,
    val description: String,
    val quality: String,
    val type: String,
    val dropRate: Double?
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
}

