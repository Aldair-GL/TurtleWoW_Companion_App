package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.SearchHistoryDao
import com.example.turtlewowcompanion.data.local.dao.ZoneDao
import com.example.turtlewowcompanion.data.local.entity.SearchHistoryEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.domain.model.SearchResult
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SearchRepository(
    private val api: TurtleWowApi,
    private val searchHistoryDao: SearchHistoryDao,
    private val zoneDao: ZoneDao
) {
    /**
     * Búsqueda local: combina zonas cacheadas en Room + razas y clases del backend.
     * No depende de un endpoint de búsqueda dedicado.
     */
    suspend fun search(query: String): UiState<List<SearchResult>> {
        return try {
            searchHistoryDao.insert(SearchHistoryEntity(query = query))
            val q = query.lowercase()
            val results = mutableListOf<SearchResult>()

            // Buscar en zonas (Room local)
            val zones = zoneDao.observeAll().first()
            zones.filter { it.name.lowercase().contains(q) }.forEach { z ->
                results.add(SearchResult(z.id, z.name, "zone", "${z.zoneType} · Nivel ${z.minLevel}-${z.maxLevel}"))
            }

            // Buscar en razas (backend)
            try {
                val races = api.getRaces()
                races.filter { it.name.lowercase().contains(q) }.forEach { r ->
                    results.add(SearchResult(r.id, r.name, "race", r.factionName ?: "Neutral"))
                }
            } catch (_: Exception) { /* sin conexión, se omite */ }

            // Buscar en clases (backend)
            try {
                val classes = api.getClasses()
                classes.filter { it.name.lowercase().contains(q) }.forEach { c ->
                    results.add(SearchResult(c.id, c.name, "class", c.role ?: ""))
                }
            } catch (_: Exception) { /* sin conexión, se omite */ }

            // Buscar en profesiones (backend)
            try {
                val professions = api.getProfessions()
                professions.filter { it.name.lowercase().contains(q) }.forEach { p ->
                    results.add(SearchResult(p.id, p.name, "profession", p.type ?: ""))
                }
            } catch (_: Exception) { /* sin conexión, se omite */ }

            // Buscar en items (backend)
            try {
                val items = api.getItems(page = 0, size = 200)
                items.content.filter { it.name.lowercase().contains(q) }.forEach { i ->
                    results.add(SearchResult(i.id, i.name, "item", "${i.quality ?: ""} · ${i.type ?: ""}"))
                }
            } catch (_: Exception) { /* sin conexión, se omite */ }

            UiState.Success(results)
        } catch (e: Exception) {
            UiState.Error("Error en la búsqueda: ${e.localizedMessage}")
        }
    }

    fun observeSearchHistory(): Flow<List<String>> =
        searchHistoryDao.observeRecent().map { list -> list.map { it.query } }

    suspend fun clearHistory() = searchHistoryDao.deleteAll()
}

