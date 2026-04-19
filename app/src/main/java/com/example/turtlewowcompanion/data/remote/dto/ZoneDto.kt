package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ZoneDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("minLevel") val minLevel: Int,
    @SerializedName("maxLevel") val maxLevel: Int,
    @SerializedName("continent") val continent: String,
    @SerializedName("zoneType") val zoneType: String,
    @SerializedName("factionName") val factionName: String?
)
