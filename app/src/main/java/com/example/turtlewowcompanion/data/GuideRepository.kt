package com.example.turtlewowcompanion.data

import com.example.turtlewowcompanion.data.local.GuideDao
import com.example.turtlewowcompanion.data.local.GuideEntryEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
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
            val entries = when (category) {
                GuideCategory.OBJECTS -> listOf(
                    "Bolsa de Viajero" to "Amplía inventario al inicio de aventura.",
                    "Piedra de hogar" to "Permite volver a una posada vinculada.",
                    "Botas de rapidez" to "Mejoran la movilidad en rutas largas."
                )
                GuideCategory.LORE -> listOf(
                    "Historia de Turtle WoW" to "Servidor centrado en experiencia clásica con contenido nuevo.",
                    "Nuevas zonas" to "Expansión de zonas y cadenas de misiones en Azeroth.",
                    "Rol y comunidad" to "Enfoque fuerte en comunidad y progresión orgánica."
                )
                GuideCategory.MAPS -> listOf(
                    "Bosque de Elwynn" to "Zona inicial de la Alianza con rutas de farmeo seguras.",
                    "Los Baldíos" to "Amplia zona de la Horda con quests encadenadas.",
                    "Vega de Tuercespina" to "Área mixta preparada para PvP y exploración."
                )
                GuideCategory.CLASSES -> listOf(
                    "Guerrero" to "Tanque/melee versátil, gran escalado con equipo.",
                    "Mago" to "DPS de rango con control de masas y daño explosivo.",
                    "Druida" to "Clase híbrida de soporte, tanque, melee o caster."
                )
            }

            return entries.mapIndexed { index, (title, description) ->
                GuideEntryEntity(
                    id = "${category.name.lowercase()}-$index",
                    category = category.name,
                    title = title,
                    description = description
                )
            }
        }
    }
}
