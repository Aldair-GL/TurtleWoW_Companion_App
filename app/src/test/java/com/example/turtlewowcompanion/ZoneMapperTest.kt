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
            minLevel = 1,
            maxLevel = 10,
            continent = "EASTERN_KINGDOMS",
            zoneType = "OPEN_WORLD",
            factionName = "Alliance"
        )

        val domain = dto.toDomain()

        assertEquals(1L, domain.id)
        assertEquals("Elwynn Forest", domain.name)
        assertEquals("Starting zone for Humans", domain.description)
        assertEquals(1, domain.minLevel)
        assertEquals(10, domain.maxLevel)
        assertEquals("Alliance", domain.factionName)
        assertEquals("Reinos del Este", domain.continentLabel)
        assertEquals("1-10", domain.level)
    }

    @Test
    fun `ZoneDto se convierte a entity para cache`() {
        val dto = ZoneDto(
            id = 2L,
            name = "The Barrens",
            description = "Vast Horde territory",
            minLevel = 10,
            maxLevel = 25,
            continent = "KALIMDOR",
            zoneType = "OPEN_WORLD",
            factionName = "Horde"
        )

        val entity = dto.toEntity()

        assertEquals(2L, entity.id)
        assertEquals("The Barrens", entity.name)
        assertEquals("Horde", entity.factionName)
        assertEquals("KALIMDOR", entity.continent)
    }

    @Test
    fun `ZoneCacheEntity se convierte a dominio`() {
        val entity = ZoneCacheEntity(
            id = 3L,
            name = "Stranglethorn Vale",
            description = "Dangerous jungle",
            minLevel = 30,
            maxLevel = 45,
            continent = "EASTERN_KINGDOMS",
            zoneType = "OPEN_WORLD",
            factionName = null
        )

        val domain = entity.toDomain()

        assertEquals(3L, domain.id)
        assertEquals("Stranglethorn Vale", domain.name)
        assertEquals("Dangerous jungle", domain.description)
        assertEquals("30-45", domain.level)
        assertEquals("Neutral", domain.faction)
    }
}

