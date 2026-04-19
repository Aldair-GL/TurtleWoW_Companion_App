package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Boss
import com.example.turtlewowcompanion.domain.model.LootItem
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BossRepository(private val api: TurtleWowApi) {

    fun getZoneBosses(zoneId: Long): Flow<UiState<List<Boss>>> = flow {
        emit(UiState.Loading)
        try {
            val bosses = api.getZoneBosses(zoneId).map { it.toDomain() }
            emit(UiState.Success(bosses))
        } catch (e: Exception) {
            emit(UiState.Error("Error al cargar jefes: ${e.localizedMessage}"))
        }
    }

    suspend fun getBossById(id: Long): UiState<Boss> {
        return try {
            UiState.Success(api.getBossById(id).toDomain())
        } catch (e: Exception) {
            UiState.Error("Jefe no encontrado")
        }
    }

    private fun com.example.turtlewowcompanion.data.remote.dto.BossDto.toDomain() = Boss(
        id = id,
        name = name,
        description = description ?: "",
        lore = lore ?: "",
        level = level,
        health = health,
        zoneName = zoneName ?: "",
        lootItems = lootItems?.map { it.toDomain() } ?: emptyList()
    )

    private fun com.example.turtlewowcompanion.data.remote.dto.LootItemDto.toDomain() = LootItem(
        id = id,
        name = name,
        description = description ?: "",
        quality = quality ?: "COMMON",
        type = type ?: "",
        dropRate = dropRate
    )
}

