package com.example.turtlewowcompanion.data.remote.dto
import com.google.gson.annotations.SerializedName
data class RaceDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("factionName") val factionName: String = "Neutral",
    @SerializedName("availableClasses") val availableClasses: List<String> = emptyList()
)
