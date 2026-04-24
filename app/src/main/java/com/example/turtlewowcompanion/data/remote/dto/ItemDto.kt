package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("quality") val quality: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("subtype") val subtype: String?,
    @SerializedName("levelRequired") val levelRequired: Int?,
    @SerializedName("itemLevel") val itemLevel: Int?,
    @SerializedName("professionName") val professionName: String?
)

