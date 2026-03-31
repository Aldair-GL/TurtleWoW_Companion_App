package com.example.turtlewowcompanion.ui.screens.npcs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.NpcRepository
import com.example.turtlewowcompanion.domain.model.Npc
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NpcViewModel(private val repository: NpcRepository) : ViewModel() {

    private val _npcsState = MutableStateFlow<UiState<List<Npc>>>(UiState.Loading)
    val npcsState: StateFlow<UiState<List<Npc>>> = _npcsState.asStateFlow()

    private val _npcDetailState = MutableStateFlow<UiState<Npc>>(UiState.Loading)
    val npcDetailState: StateFlow<UiState<Npc>> = _npcDetailState.asStateFlow()

    init {
        loadNpcs()
    }

    fun loadNpcs() {
        viewModelScope.launch {
            repository.getNpcs().collect { state ->
                _npcsState.value = state
            }
        }
    }

    fun loadNpcById(id: Long) {
        viewModelScope.launch {
            _npcDetailState.value = UiState.Loading
            _npcDetailState.value = repository.getNpcById(id)
        }
    }

    class Factory(private val repository: NpcRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NpcViewModel(repository) as T
        }
    }
}

