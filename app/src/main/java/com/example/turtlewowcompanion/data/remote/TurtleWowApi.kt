package com.example.turtlewowcompanion.data.remote

import com.example.turtlewowcompanion.data.remote.dto.AuthRequestDto
import com.example.turtlewowcompanion.data.remote.dto.BossDto
import com.example.turtlewowcompanion.data.remote.dto.BossKillCreateDto
import com.example.turtlewowcompanion.data.remote.dto.BossKillDto
import com.example.turtlewowcompanion.data.remote.dto.CharacterCreateRequestDto
import com.example.turtlewowcompanion.data.remote.dto.CharacterDto
import com.example.turtlewowcompanion.data.remote.dto.DungeonProgressDto
import com.example.turtlewowcompanion.data.remote.dto.DungeonProgressUpdateDto
import com.example.turtlewowcompanion.data.remote.dto.FactionDto
import com.example.turtlewowcompanion.data.remote.dto.ItemDto
import com.example.turtlewowcompanion.data.remote.dto.LootCreateDto
import com.example.turtlewowcompanion.data.remote.dto.LootDeleteDto
import com.example.turtlewowcompanion.data.remote.dto.LootProgressDto
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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PUT
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

    // ── Tracking de usuario: personajes ──────────────────────────────
    @GET("api/v1/users/{userId}/characters")
    suspend fun getCharacters(@Path("userId") userId: Long): List<CharacterDto>

    @retrofit2.http.POST("api/v1/users/{userId}/characters")
    suspend fun createCharacter(
        @Path("userId") userId: Long,
        @Body request: CharacterCreateRequestDto
    ): CharacterDto

    @DELETE("api/v1/users/{userId}/characters/{characterId}")
    suspend fun deleteCharacter(
        @Path("userId") userId: Long,
        @Path("characterId") characterId: Long
    )

    // ── Tracking de usuario: mazmorras ───────────────────────────────
    @GET("api/v1/users/{userId}/dungeon-progress")
    suspend fun getDungeonProgress(@Path("userId") userId: Long): List<DungeonProgressDto>

    @PUT("api/v1/users/{userId}/dungeon-progress/{zoneId}")
    suspend fun upsertDungeonProgress(
        @Path("userId") userId: Long,
        @Path("zoneId") zoneId: Long,
        @Body body: DungeonProgressUpdateDto
    ): DungeonProgressDto

    // ── Tracking de usuario: jefes derrotados ────────────────────────
    @GET("api/v1/users/{userId}/boss-kills")
    suspend fun getBossKills(@Path("userId") userId: Long): List<BossKillDto>

    @retrofit2.http.POST("api/v1/users/{userId}/boss-kills")
    suspend fun createBossKill(
        @Path("userId") userId: Long,
        @Body body: BossKillCreateDto
    ): BossKillDto

    @DELETE("api/v1/users/{userId}/boss-kills/{bossId}")
    suspend fun deleteBossKill(
        @Path("userId") userId: Long,
        @Path("bossId") bossId: Long
    )

    // ── Tracking de usuario: loot ─────────────────────────────────────
    @GET("api/v1/users/{userId}/loot")
    suspend fun getLoot(@Path("userId") userId: Long): List<LootProgressDto>

    @retrofit2.http.POST("api/v1/users/{userId}/loot")
    suspend fun createLoot(
        @Path("userId") userId: Long,
        @Body body: LootCreateDto
    ): LootProgressDto

    @HTTP(method = "DELETE", path = "api/v1/users/{userId}/loot", hasBody = true)
    suspend fun deleteLoot(
        @Path("userId") userId: Long,
        @Body body: LootDeleteDto
    )

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
