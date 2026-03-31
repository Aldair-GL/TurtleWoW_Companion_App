package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestCacheEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val level: Int,
    val minLevel: Int,
    val zone: String,
    val faction: String,
    val rewardXp: Int,
    val imageUrl: String?
)

