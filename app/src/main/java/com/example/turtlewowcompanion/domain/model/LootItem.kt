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
        "COMMON"    -> "Comun"
        "UNCOMMON"  -> "Poco comun"
        "RARE"      -> "Raro"
        "EPIC"      -> "Epico"
        "LEGENDARY" -> "Legendario"
        else        -> quality
    }
}

