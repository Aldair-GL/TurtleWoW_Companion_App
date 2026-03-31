package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.QuestDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Quest
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class QuestRepository(
    private val api: TurtleWowApi,
    private val questDao: QuestDao
) {
    fun getQuests(): Flow<UiState<List<Quest>>> = flow {
        emit(UiState.Loading)
        try {
            val remote = api.getQuests()
            questDao.upsertAll(remote.map { it.toEntity() })
            questDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedQuestsOrError("Sin conexion. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedQuestsOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getQuestById(id: Long): UiState<Quest> {
        return try {
            val remote = api.getQuestById(id)
            UiState.Success(remote.toDomain())
        } catch (_: Exception) {
            val cached = questDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Quest no encontrada")
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Quest>>>.emitCachedQuestsOrError(
        message: String
    ) {
        val count = questDao.count()
        if (count > 0) {
            questDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}
