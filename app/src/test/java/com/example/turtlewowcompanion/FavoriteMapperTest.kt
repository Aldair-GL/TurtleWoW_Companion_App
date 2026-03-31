package com.example.turtlewowcompanion

import com.example.turtlewowcompanion.data.local.entity.FavoriteEntity
import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.domain.model.FavoriteItem
import com.example.turtlewowcompanion.domain.model.FavoriteType
import com.example.turtlewowcompanion.data.mapper.toEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteMapperTest {

    @Test
    fun `entity de favorito se convierte a dominio con tipo correcto`() {
        val entity = FavoriteEntity(
            id = 1L,
            refId = 42L,
            type = "ZONE",
            name = "Elwynn Forest",
            subtitle = "Zona · Nivel 1-10",
            timestamp = 1000L
        )

        val domain = entity.toDomain()

        assertEquals(1L, domain.id)
        assertEquals(42L, domain.refId)
        assertEquals(FavoriteType.ZONE, domain.type)
        assertEquals("Elwynn Forest", domain.name)
        assertEquals(1000L, domain.timestamp)
    }

    @Test
    fun `favorito de dominio se convierte a entity con tipo string`() {
        val item = FavoriteItem(
            id = 0L,
            refId = 10L,
            type = FavoriteType.QUEST,
            name = "The Missing Diplomat",
            subtitle = "Quest · Nivel 30",
            timestamp = 2000L
        )

        val entity = item.toEntity()

        assertEquals("QUEST", entity.type)
        assertEquals(10L, entity.refId)
        assertEquals("The Missing Diplomat", entity.name)
    }

    @Test
    fun `ida y vuelta entity-dominio-entity conserva los datos`() {
        val original = FavoriteEntity(
            id = 5L,
            refId = 99L,
            type = "NPC",
            name = "Hogger",
            subtitle = "Boss · Elwynn Forest",
            timestamp = 3000L
        )

        val roundTripped = original.toDomain().toEntity()

        assertEquals(original.refId, roundTripped.refId)
        assertEquals(original.type, roundTripped.type)
        assertEquals(original.name, roundTripped.name)
        assertEquals(original.subtitle, roundTripped.subtitle)
    }
}

