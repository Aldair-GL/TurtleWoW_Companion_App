package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val refId: Long,
    val type: String,       // ZONE, QUEST, NPC
    val name: String,
    val subtitle: String,
    val timestamp: Long = System.currentTimeMillis()
)

