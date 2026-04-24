package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.ItemCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.ItemDto
import com.example.turtlewowcompanion.domain.model.Item

fun ItemDto.toDomain(): Item = Item(
    id = id,
    name = name,
    description = description ?: "",
    quality = quality ?: "COMMON",
    type = type ?: "MISC",
    subtype = subtype ?: "",
    levelRequired = levelRequired,
    itemLevel = itemLevel,
    professionName = professionName
)

fun ItemDto.toEntity(): ItemCacheEntity = ItemCacheEntity(
    id = id,
    name = name,
    description = description ?: "",
    quality = quality ?: "COMMON",
    type = type ?: "MISC",
    subtype = subtype ?: "",
    levelRequired = levelRequired,
    itemLevel = itemLevel,
    professionName = professionName
)

fun ItemCacheEntity.toDomain(): Item = Item(
    id = id,
    name = name,
    description = description,
    quality = quality,
    type = type,
    subtype = subtype,
    levelRequired = levelRequired,
    itemLevel = itemLevel,
    professionName = professionName
)

