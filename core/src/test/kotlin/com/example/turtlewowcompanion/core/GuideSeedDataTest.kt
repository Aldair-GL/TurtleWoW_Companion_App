package com.example.turtlewowcompanion.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GuideSeedDataTest {
    @Test
    fun `all categories provide non-empty localized seed entries`() {
        GuideCategory.entries.forEach { category ->
            val entries = GuideSeedData.seedFor(category)
            assertTrue(entries.isNotEmpty())
            assertTrue(entries.all { it.title.isNotBlank() && it.description.isNotBlank() })
            assertEquals(entries.size, entries.distinctBy { it.title }.size)
        }
    }
}
