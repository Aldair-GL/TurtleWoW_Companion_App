package com.example.turtlewowcompanion.domain.model
data class Race(
    val id: Long,
    val name: String,
    val description: String,
    val factionName: String,
    val availableClasses: List<String>
)
