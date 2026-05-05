package com.example.turtlewowcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "loot_progress",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index(value = ["userId", "itemName", "bossId"], unique = true)]
)
data class LootProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val itemName: String,
    val itemQuality: String?,
    val bossId: Long,
    val bossName: String,
    val obtainedAt: Long = System.currentTimeMillis()
)

