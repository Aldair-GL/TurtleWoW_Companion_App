package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.ZoneDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Zone
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ZoneRepository(
    private val api: TurtleWowApi,
    private val zoneDao: ZoneDao
) {
    // Intenta red primero (respuesta paginada), si falla usa caché local
    fun getZones(): Flow<UiState<List<Zone>>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.getZones(page = 0, size = 50)
            val zones = response.content
            zoneDao.upsertAll(zones.map { it.toEntity() })
            zoneDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedZonesOrError("Sin conexión a internet. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedZonesOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getZoneById(id: Long): UiState<Zone> {
        return try {
            val remote = api.getZoneById(id)
            UiState.Success(remote.toDomain())
        } catch (_: Exception) {
            val cached = zoneDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Zona no encontrada")
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Zone>>>.emitCachedZonesOrError(
        message: String
    ) {
        val count = zoneDao.count()
        if (count > 0) {
            zoneDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}
