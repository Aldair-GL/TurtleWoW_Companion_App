package com.example.turtlewowcompanion.domain.model

data class SearchResult(
    val id: Long,
    val name: String,
    val type: String,       // "zone", "quest", "npc"
    val description: String
)

