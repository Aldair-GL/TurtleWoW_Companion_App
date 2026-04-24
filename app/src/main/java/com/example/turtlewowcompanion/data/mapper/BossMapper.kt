package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.BossCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.BossDto
import com.example.turtlewowcompanion.domain.model.Boss
import com.example.turtlewowcompanion.domain.model.LootItem

fun BossDto.toDomain(): Boss = Boss(
    id = id,
    name = name,
    description = description ?: "",
    lore = lore ?: "",
    level = level,
    health = health,
    zoneName = zoneName ?: "",
    lootItems = lootItems?.map { it.toDomain() } ?: emptyList()
)

fun BossDto.toEntity(zoneId: Long? = null): BossCacheEntity = BossCacheEntity(
    id = id,
    name = name,
    description = description ?: "",
    lore = lore ?: "",
    level = level,
    health = health,
    zoneName = zoneName ?: "",
    zoneId = zoneId
)

fun BossCacheEntity.toDomain(): Boss = Boss(
    id = id,
    name = name,
    description = description,
    lore = lore,
    level = level,
    health = health,
    zoneName = zoneName,
    lootItems = emptyList()
)

fun com.example.turtlewowcompanion.data.remote.dto.LootItemDto.toDomain(): LootItem = LootItem(
    id = id,
    name = name,
    description = description ?: "",
    quality = quality ?: "COMMON",
    type = type ?: "",
    dropRate = dropRate
)

