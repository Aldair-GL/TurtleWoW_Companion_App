package com.example.turtlewowcompanion.domain.model

data class WowClass(
    val id: Long,
    val name: String,
    val description: String,
    val role: String,
    val resourceType: String
) {
    val roleLabel: String get() = when (role) {
        "TANK"   -> "Tanque"
        "HEALER" -> "Sanador"
        "DPS"    -> "DPS"
        "HYBRID" -> "Híbrido"
        else     -> role
    }
    val resourceLabel: String get() = when (resourceType) {
        "MANA"   -> "Maná"
        "RAGE"   -> "Ira"
        "ENERGY" -> "Energía"
        else     -> resourceType
    }
}
