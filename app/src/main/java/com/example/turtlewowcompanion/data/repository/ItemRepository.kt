package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.ItemDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Item
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ItemRepository(
    private val api: TurtleWowApi,
    private val itemDao: ItemDao
) {
    fun getItems(): Flow<UiState<List<Item>>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.getItems(page = 0, size = 200)
            itemDao.upsertAll(response.content.map { it.toEntity() })
            itemDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } catch (e: IOException) {
            emitCachedOrError("Sin conexión. Mostrando datos guardados.")
        } catch (e: Exception) {
            emitCachedOrError("Error: ${e.localizedMessage}")
        }
    }

    suspend fun getItemById(id: Long): UiState<Item> {
        return try {
            val dto = api.getItemById(id)
            UiState.Success(dto.toDomain())
        } catch (_: Exception) {
            val cached = itemDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Objeto no encontrado")
        }
    }

    fun getItemsByBoss(bossId: Long): Flow<UiState<List<Item>>> = flow {
        emit(UiState.Loading)
        try {
            val items = api.getItemsByBoss(bossId).map { it.toDomain() }
            emit(UiState.Success(items))
        } catch (e: Exception) {
            emit(UiState.Error("Error al cargar objetos: ${e.localizedMessage}"))
        }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<UiState<List<Item>>>.emitCachedOrError(
        message: String
    ) {
        val count = itemDao.count()
        if (count > 0) {
            itemDao.observeAll().collect { entities ->
                emit(UiState.Success(entities.map { it.toDomain() }))
            }
        } else {
            emit(UiState.Error(message))
        }
    }
}

