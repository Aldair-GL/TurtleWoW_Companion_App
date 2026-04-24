package com.example.turtlewowcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthRequestDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class UserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String
)

