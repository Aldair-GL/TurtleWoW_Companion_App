package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.Race
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RaceRepository(private val api: TurtleWowApi) {

    fun getRaces(): Flow<UiState<List<Race>>> = flow {
        emit(UiState.Loading)
        try {
            val races = api.getRaces().map {
                Race(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    factionName = it.factionName,
                    availableClasses = it.availableClasses
                )
            }
            emit(UiState.Success(races))
        } catch (e: Exception) {
            emit(UiState.Error("Error al cargar razas: ${e.localizedMessage}"))
        }
    }

    suspend fun getRaceById(id: Long): UiState<Race> {
        return try {
            val dto = api.getRaceById(id)
            UiState.Success(
                Race(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    factionName = dto.factionName,
                    availableClasses = dto.availableClasses
                )
            )
        } catch (e: Exception) {
            UiState.Error("Raza no encontrada")
        }
    }
}

