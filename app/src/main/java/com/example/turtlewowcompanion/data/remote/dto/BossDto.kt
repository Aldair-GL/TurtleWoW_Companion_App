package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BossDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("lore") val lore: String?,
    @SerializedName("level") val level: Int?,
    @SerializedName("health") val health: Long?,
    @SerializedName("zoneName") val zoneName: String?,
    @SerializedName("lootItems") val lootItems: List<LootItemDto>?
)

