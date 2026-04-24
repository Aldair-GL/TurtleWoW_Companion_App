package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.BossDao
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Boss
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException

class BossRepository(
    private val api: TurtleWowApi,
    private val bossDao: BossDao
) {
    fun getZoneBosses(zoneId: Long): Flow<UiState<List<Boss>>> = flow {
        emit(UiState.Loading)
        try {
            val remote = api.getZoneBosses(zoneId)
            bossDao.upsertAll(remote.map { it.toEntity(zoneId) })
            val bosses = remote.map { it.toDomain() }
            emit(UiState.Success(bosses))
        } catch (e: IOException) {
            val count = bossDao.countByZone(zoneId)
            if (count > 0) {
                bossDao.observeByZone(zoneId).collect { entities ->
                    emit(UiState.Success(entities.map { it.toDomain() }))
                }
            } else {
                emit(UiState.Error("Sin conexión. No hay datos guardados."))
            }
        } catch (e: Exception) {
            emit(UiState.Error("Error al cargar jefes: ${e.localizedMessage}"))
        }
    }

    suspend fun getBossById(id: Long): UiState<Boss> {
        return try {
            UiState.Success(api.getBossById(id).toDomain())
        } catch (_: Exception) {
            val cached = bossDao.getById(id)
            if (cached != null) UiState.Success(cached.toDomain())
            else UiState.Error("Jefe no encontrado")
        }
    }
}
