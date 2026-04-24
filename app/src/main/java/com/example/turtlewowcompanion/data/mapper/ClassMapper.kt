package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.ClassCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.WowClassDto
import com.example.turtlewowcompanion.domain.model.WowClass

fun WowClassDto.toDomain(): WowClass = WowClass(
    id = id,
    name = name,
    description = description,
    role = role,
    resourceType = resourceType
)

fun WowClassDto.toEntity(): ClassCacheEntity = ClassCacheEntity(
    id = id,
    name = name,
    description = description,
    role = role,
    resourceType = resourceType
)

fun ClassCacheEntity.toDomain(): WowClass = WowClass(
    id = id,
    name = name,
    description = description,
    role = role,
    resourceType = resourceType
)

