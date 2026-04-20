package com.example.turtlewowcompanion.domain.model

data class FavoriteItem(
    val id: Long,
    val refId: Long,            // ID of the referenced entity (zone, quest, npc...)
    val type: FavoriteType,
    val name: String,
    val subtitle: String,
    val timestamp: Long
)

enum class FavoriteType {
    ZONE, QUEST, NPC, RACE, CLASS, BOSS
}

