package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.WowClass
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WowClassRepository(private val api: TurtleWowApi) {

    fun getClasses(): Flow<UiState<List<WowClass>>> = flow {
        emit(UiState.Loading)
        try {
            val classes = api.getClasses().map {
                WowClass(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    role = it.role,
                    resourceType = it.resourceType
                )
            }
            emit(UiState.Success(classes))
        } catch (e: Exception) {
            emit(UiState.Error("Error al cargar clases: ${e.localizedMessage}"))
        }
    }

    suspend fun getClassById(id: Long): UiState<WowClass> {
        return try {
            val dto = api.getClassById(id)
            UiState.Success(
                WowClass(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    role = dto.role,
                    resourceType = dto.resourceType
                )
            )
        } catch (e: Exception) {
            UiState.Error("Clase no encontrada")
        }
    }
}

