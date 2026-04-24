package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.ProfessionDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Profession
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ProfessionRepository(
    private val api: TurtleWowApi,
    private val professionDao: ProfessionDao
) {
    fun getProfessions(): Flow<UiState<List<Profession>>> = flow {
        emit(UiState.Loading)
        try {
            val remote = api.getProfessions()
            professionDao.upsertAll(remote.map { it.toEntity() })
            professionDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedOrError("Sin conexión. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getProfessionById(id: Long): UiState<Profession> {
        return try {
            val dto = api.getProfessionById(id)
            UiState.Success(dto.toDomain())
        } catch (_: Exception) {
            val cached = professionDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Profesión no encontrada")
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Profession>>>.emitCachedOrError(
        message: String
    ) {
        val count = professionDao.count()
        if (count > 0) {
            professionDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}

