package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.QuestCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.QuestDto
import com.example.turtlewowcompanion.domain.model.Quest

fun QuestDto.toDomain(): Quest = Quest(
    id = id,
    title = title,
    description = description,
    level = level,
    minLevel = minLevel,
    zone = zone,
    faction = faction,
    rewardXp = rewardXp,
    imageUrl = imageUrl
)

fun QuestDto.toEntity(): QuestCacheEntity = QuestCacheEntity(
    id = id,
    title = title,
    description = description,
    level = level,
    minLevel = minLevel,
    zone = zone,
    faction = faction,
    rewardXp = rewardXp,
    imageUrl = imageUrl
)

fun QuestCacheEntity.toDomain(): Quest = Quest(
    id = id,
    title = title,
    description = description,
    level = level,
    minLevel = minLevel,
    zone = zone,
    faction = faction,
    rewardXp = rewardXp,
    imageUrl = imageUrl
)

