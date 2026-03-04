package com.example.turtlewowcompanion.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guide_entries")
data class GuideEntryEntity(
    @PrimaryKey val id: String,
    val category: String,
    val title: String,
    val description: String
)
