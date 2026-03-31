package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zones")
data class ZoneCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val level: String,
    val faction: String,
    val imageUrl: String?
)

