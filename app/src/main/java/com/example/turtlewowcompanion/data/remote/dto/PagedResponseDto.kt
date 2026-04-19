package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PagedResponseDto<T>(
    @SerializedName("content") val content: List<T>,
    @SerializedName("totalElements") val totalElements: Long,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("first") val first: Boolean,
    @SerializedName("last") val last: Boolean
)

