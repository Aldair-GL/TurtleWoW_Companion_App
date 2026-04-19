package com.example.turtlewowcompanion.domain.model

data class Boss(
    val id: Long,
    val name: String,
    val description: String,
    val lore: String,
    val level: Int?,
    val health: Long?,
    val zoneName: String,
    val lootItems: List<LootItem>
)

