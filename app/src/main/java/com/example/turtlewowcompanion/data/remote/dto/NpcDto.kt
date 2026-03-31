package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NpcDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("zone") val zone: String,
    @SerializedName("type") val type: String,
    @SerializedName("level") val level: Int,
    @SerializedName("faction") val faction: String,
    @SerializedName("imageUrl") val imageUrl: String?
)

