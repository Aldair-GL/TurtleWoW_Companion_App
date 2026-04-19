package com.example.turtlewowcompanion.data.remote.dto
import com.google.gson.annotations.SerializedName
data class FactionDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String
)
