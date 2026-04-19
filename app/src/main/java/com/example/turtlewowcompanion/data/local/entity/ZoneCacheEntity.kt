package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zones")
data class ZoneCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val minLevel: Int,
    val maxLevel: Int,
    val continent: String,
    val zoneType: String,
    val factionName: String?
)

