package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ZoneDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("level") val level: String,
    @SerializedName("faction") val faction: String,
    @SerializedName("imageUrl") val imageUrl: String?
)

