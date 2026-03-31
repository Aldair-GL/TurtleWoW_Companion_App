package com.example.turtlewowcompanion

import com.example.turtlewowcompanion.data.local.entity.ZoneCacheEntity
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.mapper.toEntity
import com.example.turtlewowcompanion.data.remote.dto.ZoneDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ZoneMapperTest {

    @Test
    fun `ZoneDto se convierte a dominio correctamente`() {
        val dto = ZoneDto(
            id = 1L,
            name = "Elwynn Forest",
            description = "Starting zone for Humans",
            level = "1-10",
            faction = "Alliance",
            imageUrl = "http://example.com/elwynn.png"
        )

        val domain = dto.toDomain()

        assertEquals(1L, domain.id)
        assertEquals("Elwynn Forest", domain.name)
        assertEquals("Starting zone for Humans", domain.description)
        assertEquals("1-10", domain.level)
        assertEquals("Alliance", domain.faction)
        assertEquals("http://example.com/elwynn.png", domain.imageUrl)
    }

    @Test
    fun `ZoneDto se convierte a entity para cache`() {
        val dto = ZoneDto(
            id = 2L,
            name = "The Barrens",
            description = "Vast Horde territory",
            level = "10-25",
            faction = "Horde",
            imageUrl = null
        )

        val entity = dto.toEntity()

        assertEquals(2L, entity.id)
        assertEquals("The Barrens", entity.name)
        assertEquals("Horde", entity.faction)
        assertEquals(null, entity.imageUrl)
    }

    @Test
    fun `ZoneCacheEntity se convierte a dominio`() {
        val entity = ZoneCacheEntity(
            id = 3L,
            name = "Stranglethorn Vale",
            description = "Dangerous jungle",
            level = "30-45",
            faction = "Neutral",
            imageUrl = "http://example.com/stv.png"
        )

        val domain = entity.toDomain()

        assertEquals(3L, domain.id)
        assertEquals("Stranglethorn Vale", domain.name)
        assertEquals("Dangerous jungle", domain.description)
        assertEquals("30-45", domain.level)
        assertEquals("Neutral", domain.faction)
    }
}



