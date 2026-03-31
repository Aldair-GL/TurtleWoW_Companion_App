package com.example.turtlewowcompanion.data.mapper

import com.example.turtlewowcompanion.data.remote.dto.SearchResultDto
import com.example.turtlewowcompanion.domain.model.SearchResult

fun SearchResultDto.toDomain(): SearchResult = SearchResult(
    id = id,
    name = name,
    type = type,
    description = description
)

