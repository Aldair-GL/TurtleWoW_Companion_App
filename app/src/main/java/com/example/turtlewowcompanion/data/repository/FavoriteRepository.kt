package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.FavoriteDao
import com.example.turtlewowcompanion.data.local.entity.FavoriteEntity
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.domain.model.FavoriteItem
import com.example.turtlewowcompanion.domain.model.FavoriteType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {
    fun observeAll(): Flow<List<FavoriteItem>> =
        favoriteDao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun toggleFavorite(refId: Long, type: FavoriteType, name: String, subtitle: String) {
        val existing = favoriteDao.findByRefAndType(refId, type.name)
        if (existing != null) {
            favoriteDao.deleteByRefAndType(refId, type.name)
        } else {
            favoriteDao.insert(
                FavoriteEntity(
                    refId = refId,
                    type = type.name,
                    name = name,
                    subtitle = subtitle
                )
            )
        }
    }

    suspend fun isFavorite(refId: Long, type: FavoriteType): Boolean =
        favoriteDao.findByRefAndType(refId, type.name) != null

    suspend fun deleteAll() = favoriteDao.deleteAll()
}

