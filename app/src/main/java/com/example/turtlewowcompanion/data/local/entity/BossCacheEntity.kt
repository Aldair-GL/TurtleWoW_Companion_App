package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bosses")
data class BossCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val lore: String,
    val level: Int?,
    val health: Long?,
    val zoneName: String,
    val zoneId: Long?
)

