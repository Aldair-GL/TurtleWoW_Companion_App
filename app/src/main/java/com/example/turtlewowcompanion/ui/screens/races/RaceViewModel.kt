package com.example.turtlewowcompanion.ui.screens.races

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.RaceRepository
import com.example.turtlewowcompanion.domain.model.Race
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RaceViewModel(private val repository: RaceRepository) : ViewModel() {

    private val _racesState = MutableStateFlow<UiState<List<Race>>>(UiState.Loading)
    val racesState: StateFlow<UiState<List<Race>>> = _racesState.asStateFlow()

    private val _raceDetailState = MutableStateFlow<UiState<Race>>(UiState.Loading)
    val raceDetailState: StateFlow<UiState<Race>> = _raceDetailState.asStateFlow()

    init { loadRaces() }

    fun loadRaces() {
        viewModelScope.launch {
            repository.getRaces().collect { _racesState.value = it }
        }
    }

    fun loadRaceById(id: Long) {
        viewModelScope.launch {
            _raceDetailState.value = UiState.Loading
            _raceDetailState.value = repository.getRaceById(id)
        }
    }

    class Factory(private val repository: RaceRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = RaceViewModel(repository) as T
    }
}

