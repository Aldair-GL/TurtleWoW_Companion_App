package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ZoneDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("minLevel") val minLevel: Int = 0,
    @SerializedName("maxLevel") val maxLevel: Int = 0,
    @SerializedName("continent") val continent: String = "EASTERN_KINGDOMS",
    @SerializedName("zoneType") val zoneType: String = "OPEN_WORLD",
    @SerializedName("factionName") val factionName: String? = null
)
