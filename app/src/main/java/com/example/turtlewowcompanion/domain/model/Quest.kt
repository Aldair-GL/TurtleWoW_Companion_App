package com.example.turtlewowcompanion.domain.model

data class Quest(
    val id: Long,
    val title: String,
    val description: String,
    val level: Int,
    val minLevel: Int,
    val zone: String,
    val faction: String,
    val rewardXp: Int = 0,
    val imageUrl: String? = null
)

