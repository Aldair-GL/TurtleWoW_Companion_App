package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.FavoriteEntity
import com.example.turtlewowcompanion.domain.model.FavoriteItem
import com.example.turtlewowcompanion.domain.model.FavoriteType

fun FavoriteEntity.toDomain(): FavoriteItem = FavoriteItem(
    id = id,
    refId = refId,
    type = FavoriteType.valueOf(type),
    name = name,
    subtitle = subtitle,
    timestamp = timestamp
)

fun FavoriteItem.toEntity(): FavoriteEntity = FavoriteEntity(
    id = id,
    refId = refId,
    type = type.name,
    name = name,
    subtitle = subtitle,
    timestamp = timestamp
)

