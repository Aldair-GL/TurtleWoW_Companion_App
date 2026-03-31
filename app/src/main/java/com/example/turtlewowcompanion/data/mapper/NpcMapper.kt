package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.NpcCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.NpcDto
import com.example.turtlewowcompanion.domain.model.Npc

fun NpcDto.toDomain(): Npc = Npc(
    id = id,
    name = name,
    title = title,
    zone = zone,
    type = type,
    level = level,
    faction = faction,
    imageUrl = imageUrl
)

fun NpcDto.toEntity(): NpcCacheEntity = NpcCacheEntity(
    id = id,
    name = name,
    title = title,
    zone = zone,
    type = type,
    level = level,
    faction = faction,
    imageUrl = imageUrl
)

fun NpcCacheEntity.toDomain(): Npc = Npc(
    id = id,
    name = name,
    title = title,
    zone = zone,
    type = type,
    level = level,
    faction = faction,
    imageUrl = imageUrl
)

