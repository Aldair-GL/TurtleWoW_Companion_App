package com.example.turtlewowcompanion.domain.model

data class Profession(
    val id: Long,
    val name: String,
    val description: String,
    val type: String
) {
    val typeLabel: String get() = when (type.uppercase()) {
        "PRIMARY", "PRIMARIA"     -> "Primaria"
        "SECONDARY", "SECUNDARIA" -> "Secundaria"
        else                      -> type
    }
}
