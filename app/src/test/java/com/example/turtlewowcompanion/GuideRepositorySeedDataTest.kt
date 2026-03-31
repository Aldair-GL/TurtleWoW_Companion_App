package com.example.turtlewowcompanion

import com.example.turtlewowcompanion.data.mapper.toDomain
import com.example.turtlewowcompanion.data.remote.dto.NpcDto
import com.example.turtlewowcompanion.data.remote.dto.QuestDto
import org.junit.Assert.assertEquals
import org.junit.Test

class DtoMapperTest {

    @Test
    fun `QuestDto se mapea a modelo de dominio`() {
        val dto = QuestDto(
            id = 1L,
            title = "The Missing Diplomat",
            description = "Find the diplomat",
            level = 30,
            minLevel = 28,
            zone = "Stormwind",
            faction = "Alliance",
            rewardXp = 2500,
            imageUrl = null
        )

        val quest = dto.toDomain()

        assertEquals(1L, quest.id)
        assertEquals("The Missing Diplomat", quest.title)
        assertEquals(30, quest.level)
        assertEquals(28, quest.minLevel)
        assertEquals(2500, quest.rewardXp)
    }

    @Test
    fun `NpcDto se mapea a modelo de dominio`() {
        val dto = NpcDto(
            id = 5L,
            name = "Hogger",
            title = "Gnoll Chieftain",
            zone = "Elwynn Forest",
            type = "Boss",
            level = 11,
            faction = "Hostile",
            imageUrl = null
        )

        val npc = dto.toDomain()

        assertEquals(5L, npc.id)
        assertEquals("Hogger", npc.name)
        assertEquals("Gnoll Chieftain", npc.title)
        assertEquals("Boss", npc.type)
        assertEquals(11, npc.level)
    }
}
