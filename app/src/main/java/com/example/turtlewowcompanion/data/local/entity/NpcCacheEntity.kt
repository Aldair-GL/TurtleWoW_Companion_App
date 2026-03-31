package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "npcs")
data class NpcCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val title: String,
    val zone: String,
    val type: String,
    val level: Int,
    val faction: String,
    val imageUrl: String?
)

