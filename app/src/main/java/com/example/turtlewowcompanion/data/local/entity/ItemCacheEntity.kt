package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val quality: String,
    val type: String,
    val subtype: String,
    val levelRequired: Int?,
    val itemLevel: Int?,
    val professionName: String?
)

