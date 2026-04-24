package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.local.entity.ProfessionCacheEntity
import com.example.turtlewowcompanion.data.remote.dto.ProfessionDto
import com.example.turtlewowcompanion.domain.model.Profession

fun ProfessionDto.toDomain(): Profession = Profession(
    id = id, name = name, description = description, type = type
)

fun ProfessionDto.toEntity(): ProfessionCacheEntity = ProfessionCacheEntity(
    id = id, name = name, description = description, type = type
)

fun ProfessionCacheEntity.toDomain(): Profession = Profession(
    id = id, name = name, description = description, type = type
)

