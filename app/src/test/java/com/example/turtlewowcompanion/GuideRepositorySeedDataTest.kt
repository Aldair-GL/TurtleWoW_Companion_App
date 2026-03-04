package com.example.turtlewowcompanion

import com.example.turtlewowcompanion.data.GuideCategory
import com.example.turtlewowcompanion.data.GuideRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GuideRepositorySeedDataTest {

    @Test
    fun `seed data exists for all categories`() {
        GuideCategory.entries.forEach { category ->
            val items = GuideRepository.seedDataFor(category)

            assertTrue(items.isNotEmpty())
            assertTrue(items.all { it.category == category.name })
            assertEquals(items.distinctBy { it.id }.size, items.size)
        }
    }
}
