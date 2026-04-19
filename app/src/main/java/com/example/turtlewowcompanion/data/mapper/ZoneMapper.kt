package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.ZoneCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.ZoneDto
import com.example.turtlewowcompanion.domain.model.Zone

fun ZoneDto.toDomain(): Zone = Zone(
    id = id,
    name = name,
    description = description,
    minLevel = minLevel,
    maxLevel = maxLevel,
    continent = continent,
    zoneType = zoneType,
    factionName = factionName
)

fun ZoneDto.toEntity(): ZoneCacheEntity = ZoneCacheEntity(
    id = id,
    name = name,
    description = description,
    minLevel = minLevel,
    maxLevel = maxLevel,
    continent = continent,
    zoneType = zoneType,
    factionName = factionName
)

fun ZoneCacheEntity.toDomain(): Zone = Zone(
    id = id,
    name = name,
    description = description,
    minLevel = minLevel,
    maxLevel = maxLevel,
    continent = continent,
    zoneType = zoneType,
    factionName = factionName
)
