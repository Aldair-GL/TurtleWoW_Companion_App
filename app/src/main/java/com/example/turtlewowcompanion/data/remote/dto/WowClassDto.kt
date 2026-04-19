package com.example.turtlewowcompanion.data.remote.dto
import com.google.gson.annotations.SerializedName
data class WowClassDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("role") val role: String,
    @SerializedName("resourceType") val resourceType: String
)
