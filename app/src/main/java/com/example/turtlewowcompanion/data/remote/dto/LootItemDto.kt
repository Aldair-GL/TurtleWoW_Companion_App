package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LootItemDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("quality") val quality: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("dropRate") val dropRate: Double?
)

