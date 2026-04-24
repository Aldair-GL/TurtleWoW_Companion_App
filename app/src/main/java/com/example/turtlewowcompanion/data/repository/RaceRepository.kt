package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.RaceDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Race
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RaceRepository(
    private val api: TurtleWowApi,
    private val raceDao: RaceDao
) {
    fun getRaces(): Flow<UiState<List<Race>>> = flow {
        emit(UiState.Loading)
        try {
            val remote = api.getRaces()
            raceDao.upsertAll(remote.map { it.toEntity() })
            raceDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedOrError("Sin conexión. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getRaceById(id: Long): UiState<Race> {
        return try {
            val dto = api.getRaceById(id)
            UiState.Success(dto.toDomain())
        } catch (_: Exception) {
            val cached = raceDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Raza no encontrada")
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Race>>>.emitCachedOrError(
        message: String
    ) {
        val count = raceDao.count()
        if (count > 0) {
            raceDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}
