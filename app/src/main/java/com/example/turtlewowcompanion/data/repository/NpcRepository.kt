package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.NpcDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Npc
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class NpcRepository(
    private val api: TurtleWowApi,
    private val npcDao: NpcDao
) {
    fun getNpcs(): Flow<UiState<List<Npc>>> = flow {
        emit(UiState.Loading)
        try {
            val remote = api.getNpcs()
            npcDao.upsertAll(remote.map { it.toEntity() })
            npcDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedNpcsOrError("Sin conexión. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedNpcsOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getNpcById(id: Long): UiState<Npc> {
        return try {
            val remote = api.getNpcById(id)
            UiState.Success(remote.toDomain())
        } catch (_: Exception) {
            val cached = npcDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("NPC no encontrado")
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Npc>>>.emitCachedNpcsOrError(
        message: String
    ) {
        val count = npcDao.count()
        if (count > 0) {
            npcDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}
