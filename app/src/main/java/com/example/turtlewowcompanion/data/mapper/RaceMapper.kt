package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.RaceCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.RaceDto
import com.example.turtlewowcompanion.domain.model.Race

fun RaceDto.toDomain(): Race = Race(
    id = id,
    name = name,
    description = description,
    factionName = factionName,
    availableClasses = availableClasses
)

fun RaceDto.toEntity(): RaceCacheEntity = RaceCacheEntity(
    id = id,
    name = name,
    description = description,
    factionName = factionName,
    availableClasses = availableClasses.joinToString(",")
)

fun RaceCacheEntity.toDomain(): Race = Race(
    id = id,
    name = name,
    description = description,
    factionName = factionName,
    availableClasses = availableClasses.split(",").filter { it.isNotBlank() }
)

