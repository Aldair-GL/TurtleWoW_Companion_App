package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

// ── Personajes ──────────────────────────────────────────────────────────
data class CharacterDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("raceName") val raceName: String,
    @SerializedName("className") val className: String,
    @SerializedName("level") val level: Int,
    @SerializedName("createdAt") val createdAt: String? = null
)

data class CharacterCreateRequestDto(
    @SerializedName("name") val name: String,
    @SerializedName("raceName") val raceName: String,
    @SerializedName("className") val className: String,
    @SerializedName("level") val level: Int
)

// ── Mazmorras ───────────────────────────────────────────────────────────
data class DungeonProgressDto(
    @SerializedName("id") val id: Long,
    @SerializedName("zoneId") val zoneId: Long,
    @SerializedName("zoneName") val zoneName: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("completedAt") val completedAt: String? = null
)

data class DungeonProgressUpdateDto(
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("zoneName") val zoneName: String
)

// ── Jefes derrotados ────────────────────────────────────────────────────
data class BossKillDto(
    @SerializedName("id") val id: Long,
    @SerializedName("bossId") val bossId: Long,
    @SerializedName("bossName") val bossName: String,
    @SerializedName("zoneName") val zoneName: String,
    @SerializedName("killedAt") val killedAt: String? = null
)

data class BossKillCreateDto(
    @SerializedName("bossId") val bossId: Long,
    @SerializedName("bossName") val bossName: String,
    @SerializedName("zoneName") val zoneName: String
)

// ── Loot conseguido ─────────────────────────────────────────────────────
data class LootProgressDto(
    @SerializedName("id") val id: Long,
    @SerializedName("itemName") val itemName: String,
    @SerializedName("itemQuality") val itemQuality: String?,
    @SerializedName("bossId") val bossId: Long,
    @SerializedName("bossName") val bossName: String,
    @SerializedName("obtainedAt") val obtainedAt: String? = null
)

data class LootCreateDto(
    @SerializedName("itemName") val itemName: String,
    @SerializedName("itemQuality") val itemQuality: String?,
    @SerializedName("bossId") val bossId: Long,
    @SerializedName("bossName") val bossName: String
)

data class LootDeleteDto(
    @SerializedName("itemName") val itemName: String,
    @SerializedName("bossId") val bossId: Long
)

