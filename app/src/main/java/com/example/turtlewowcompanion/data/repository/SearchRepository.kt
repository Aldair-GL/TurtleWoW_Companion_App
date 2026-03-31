package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.SearchHistoryDao
import com.example.turtlewowcompanion.data.local.entity.SearchHistoryEntity
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.SearchResult
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepository(
    private val api: TurtleWowApi,
    private val searchHistoryDao: SearchHistoryDao
) {
    suspend fun search(query: String): UiState<List<SearchResult>> {
        return try {
            // Guardar en historial
            searchHistoryDao.insert(SearchHistoryEntity(query = query))
            val results = api.search(query)
            UiState.Success(results.map { it.toDomain() })
        } catch (e: Exception) {
            UiState.Error("Error en la búsqueda: ${e.localizedMessage}")
        }
    }

    fun observeSearchHistory(): Flow<List<String>> =
        searchHistoryDao.observeRecent().map { list -> list.map { it.query } }

    suspend fun clearHistory() = searchHistoryDao.deleteAll()
}

