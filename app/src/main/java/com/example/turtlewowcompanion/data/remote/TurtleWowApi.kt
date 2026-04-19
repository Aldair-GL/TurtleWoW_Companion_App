package com.example.turtlewowcompanion.data.remote

import com.example.turtlewowcompanion.data.remote.dto.FactionDto
import com.example.turtlewowcompanion.data.remote.dto.NpcDto
import com.example.turtlewowcompanion.data.remote.dto.PagedResponseDto
import com.example.turtlewowcompanion.data.remote.dto.ProfessionDto
import com.example.turtlewowcompanion.data.remote.dto.QuestDto
import com.example.turtlewowcompanion.data.remote.dto.RaceDto
import com.example.turtlewowcompanion.data.remote.dto.SearchResultDto
import com.example.turtlewowcompanion.data.remote.dto.WowClassDto
import com.example.turtlewowcompanion.data.remote.dto.ZoneDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TurtleWowApi {

    // ── Zonas (paginado) ────────────────────────────────────────────────────
    @GET("api/v1/zones")
    suspend fun getZones(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50,
        @Query("sort") sort: String = "name"
    ): PagedResponseDto<ZoneDto>

    @GET("api/v1/zones")
    suspend fun getZonesByName(
        @Query("name") name: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): PagedResponseDto<ZoneDto>

    @GET("api/v1/zones")
    suspend fun getZonesByContinent(
        @Query("continent") continent: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): PagedResponseDto<ZoneDto>

    @GET("api/v1/zones/{id}")
    suspend fun getZoneById(@Path("id") id: Long): ZoneDto

    // ── Razas ────────────────────────────────────────────────────────────────
    @GET("api/v1/races")
    suspend fun getRaces(): List<RaceDto>

    @GET("api/v1/races/{id}")
    suspend fun getRaceById(@Path("id") id: Long): RaceDto

    // ── Clases ───────────────────────────────────────────────────────────────
    @GET("api/v1/classes")
    suspend fun getClasses(): List<WowClassDto>

    @GET("api/v1/classes/{id}")
    suspend fun getClassById(@Path("id") id: Long): WowClassDto

    // ── Facciones ────────────────────────────────────────────────────────────
    @GET("api/v1/factions")
    suspend fun getFactions(): List<FactionDto>

    @GET("api/v1/factions/{id}")
    suspend fun getFactionById(@Path("id") id: Long): FactionDto

    // ── Profesiones ──────────────────────────────────────────────────────────
    @GET("api/v1/professions")
    suspend fun getProfessions(): List<ProfessionDto>

    @GET("api/v1/professions/{id}")
    suspend fun getProfessionById(@Path("id") id: Long): ProfessionDto

    // ── Búsqueda (legacy, puede no existir en backend) ──────────────────────
    @GET("api/search")
    suspend fun search(@Query("q") query: String): List<SearchResultDto>

    // ── Endpoints legacy (ya no usados, mantenidos para compilación) ─────────
    @GET("api/quests")
    suspend fun getQuests(): List<QuestDto>

    @GET("api/quests/{id}")
    suspend fun getQuestById(@Path("id") id: Long): QuestDto

    @GET("api/npcs")
    suspend fun getNpcs(): List<NpcDto>

    @GET("api/npcs/{id}")
    suspend fun getNpcById(@Path("id") id: Long): NpcDto
}
