package com.example.turtlewowcompanion

import com.example.turtlewowcompanion.ui.common.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UiStateTest {

    @Test
    fun `Loading es singleton`() {
        val state: UiState<String> = UiState.Loading
        assertTrue(state is UiState.Loading)
    }

    @Test
    fun `Success contiene los datos`() {
        val data = listOf("Elwynn", "Barrens")
        val state = UiState.Success(data)

        assertTrue(state is UiState.Success)
        assertEquals(2, state.data.size)
        assertEquals("Elwynn", state.data[0])
    }

    @Test
    fun `Error contiene el mensaje`() {
        val state = UiState.Error("Network error")

        assertTrue(state is UiState.Error)
        assertEquals("Network error", state.message)
    }

    @Test
    fun `Success con lista vacia sigue siendo Success`() {
        val state = UiState.Success(emptyList<String>())
        assertTrue(state is UiState.Success)
        assertTrue(state.data.isEmpty())
    }
}
