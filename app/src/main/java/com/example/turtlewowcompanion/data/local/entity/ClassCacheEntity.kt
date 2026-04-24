package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val role: String,
    val resourceType: String
)

