package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("level") val level: Int,
    @SerializedName("minLevel") val minLevel: Int,
    @SerializedName("zone") val zone: String,
    @SerializedName("faction") val faction: String,
    @SerializedName("rewardXp") val rewardXp: Int,
    @SerializedName("imageUrl") val imageUrl: String?
)

