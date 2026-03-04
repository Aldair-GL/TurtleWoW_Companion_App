package com.example.turtlewowcompanion.data

import com.example.turtlewowcompanion.data.local.GuideDao
import com.example.turtlewowcompanion.data.local.GuideEntryEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.core.GuideSeedData
import kotlinx.coroutines.flow.Flow

class GuideRepository(
    private val guideDao: GuideDao,
    private val api: TurtleWowApi
) {
    fun observeByCategory(category: GuideCategory): Flow<List<GuideEntryEntity>> =
        guideDao.observeByCategory(category.name)

    suspend fun refresh(category: GuideCategory) {
        val remoteItems = try {
            api.getGuideByCategory(category.apiPath)
                .map {
                    GuideEntryEntity(
                        id = it.id,
                        category = category.name,
                        title = it.title,
                        description = it.description
                    )
                }
        } catch (_: Exception) {
            emptyList()
        }

        val dataToPersist = if (remoteItems.isNotEmpty()) {
            remoteItems
        } else if (guideDao.countByCategory(category.name) == 0) {
            seedDataFor(category)
        } else {
            emptyList()
        }

        if (dataToPersist.isNotEmpty()) {
            guideDao.upsertAll(dataToPersist)
        }
    }

    companion object {
        fun seedDataFor(category: GuideCategory): List<GuideEntryEntity> {
            return GuideSeedData.seedFor(category).mapIndexed { index, entry ->
                GuideEntryEntity(
                    id = "${category.name.lowercase()}-$index",
                    category = category.name,
                    title = entry.title,
                    description = entry.description
                )
            }
        }
    }
}
