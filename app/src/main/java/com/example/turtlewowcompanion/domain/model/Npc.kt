package com.example.turtlewowcompanion.domain.model

data class Npc(
    val id: Long,
    val name: String,
    val title: String,
    val zone: String,
    val type: String,           // "Vendor", "Quest Giver", "Boss", etc.
    val level: Int,
    val faction: String,
    val imageUrl: String? = null
)

