package com.example.turtlewowcompanion.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

data class GuideEntryDto(
    val id: String,
    val title: String,
    val description: String
)

interface TurtleWowApi {
    @GET("api/guia/{category}")
    suspend fun getGuideByCategory(@Path("category") category: String): List<GuideEntryDto>
}
