package com.example.turtlewowcompanion.data.remote

import com.example.turtlewowcompanion.data.remote.dto.AuthRequestDto
import com.example.turtlewowcompanion.data.remote.dto.BossDto
import com.example.turtlewowcompanion.data.remote.dto.FactionDto
import com.example.turtlewowcompanion.data.remote.dto.ItemDto
import com.example.turtlewowcompanion.data.remote.dto.NpcDto
import com.example.turtlewowcompanion.data.remote.dto.PagedResponseDto
import com.example.turtlewowcompanion.data.remote.dto.ProfessionDto
import com.example.turtlewowcompanion.data.remote.dto.QuestDto
import com.example.turtlewowcompanion.data.remote.dto.RaceDto
import com.example.turtlewowcompanion.data.remote.dto.SearchResultDto
import com.example.turtlewowcompanion.data.remote.dto.UserDto
import com.example.turtlewowcompanion.data.remote.dto.WowClassDto
import com.example.turtlewowcompanion.data.remote.dto.ZoneDto
import retrofit2.http.Body
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

    // ── Items (objetos del juego) ─────────────────────────────────────────
    @GET("api/v1/items")
    suspend fun getItems(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 200
    ): PagedResponseDto<ItemDto>

    @GET("api/v1/items/{id}")
    suspend fun getItemById(@Path("id") id: Long): ItemDto

    @GET("api/v1/bosses/{bossId}/items")
    suspend fun getItemsByBoss(@Path("bossId") bossId: Long): List<ItemDto>

    // ── Bosses de mazmorra ─────────────────────────────────────────────────
    @GET("api/v1/zones/{zoneId}/bosses")
    suspend fun getZoneBosses(@Path("zoneId") zoneId: Long): List<BossDto>

    @GET("api/v1/bosses/{id}")
    suspend fun getBossById(@Path("id") id: Long): BossDto

    // ── Búsqueda ──────────────────────────────────────────────────────────
    @GET("api/search")
    suspend fun search(@Query("q") query: String): List<SearchResultDto>

    // ── Autenticación ──────────────────────────────────────────────────
    @retrofit2.http.POST("api/v1/auth/register")
    suspend fun register(@Body request: AuthRequestDto): UserDto

    @retrofit2.http.POST("api/v1/auth/login")
    suspend fun login(@Body request: AuthRequestDto): UserDto

    // ── Endpoints legacy ─────────────────────────────────────────────────
    @GET("api/quests")
    suspend fun getQuests(): List<QuestDto>

    @GET("api/quests/{id}")
    suspend fun getQuestById(@Path("id") id: Long): QuestDto

    @GET("api/npcs")
    suspend fun getNpcs(): List<NpcDto>

    @GET("api/npcs/{id}")
    suspend fun getNpcById(@Path("id") id: Long): NpcDto
}
