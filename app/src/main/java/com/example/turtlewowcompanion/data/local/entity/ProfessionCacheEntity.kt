package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professions")
data class ProfessionCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val type: String
)

