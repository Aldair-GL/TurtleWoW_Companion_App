package com.example.turtlewowcompanion.domain.model

data class Zone(
    val id: Long,
    val name: String,
    val description: String,
    val level: String,          // e.g. "10-20"
    val faction: String,        // "Alliance", "Horde", "Neutral"
    val imageUrl: String? = null
)

