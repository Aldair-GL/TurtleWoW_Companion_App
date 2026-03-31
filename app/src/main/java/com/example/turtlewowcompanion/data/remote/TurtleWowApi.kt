package com.example.turtlewowcompanion.data.remote

import com.example.turtlewowcompanion.data.remote.dto.NpcDto
import com.example.turtlewowcompanion.data.remote.dto.QuestDto
import com.example.turtlewowcompanion.data.remote.dto.SearchResultDto
import com.example.turtlewowcompanion.data.remote.dto.ZoneDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TurtleWowApi {

    // Zones
    @GET("api/zones")
    suspend fun getZones(): List<ZoneDto>

    @GET("api/zones/{id}")
    suspend fun getZoneById(@Path("id") id: Long): ZoneDto

    // Quests
    @GET("api/quests")
    suspend fun getQuests(): List<QuestDto>

    @GET("api/quests/{id}")
    suspend fun getQuestById(@Path("id") id: Long): QuestDto

    // NPCs
    @GET("api/npcs")
    suspend fun getNpcs(): List<NpcDto>

    @GET("api/npcs/{id}")
    suspend fun getNpcById(@Path("id") id: Long): NpcDto

    // Search
    @GET("api/search")
    suspend fun search(@Query("q") query: String): List<SearchResultDto>
}
