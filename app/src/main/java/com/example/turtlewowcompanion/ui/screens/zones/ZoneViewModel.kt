package com.example.turtlewowcompanion.ui.screens.zones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.ZoneRepository
import com.example.turtlewowcompanion.domain.model.Zone
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ZoneViewModel(private val repository: ZoneRepository) : ViewModel() {

    private val _zonesState = MutableStateFlow<UiState<List<Zone>>>(UiState.Loading)
    val zonesState: StateFlow<UiState<List<Zone>>> = _zonesState.asStateFlow()

    private val _zoneDetailState = MutableStateFlow<UiState<Zone>>(UiState.Loading)
    val zoneDetailState: StateFlow<UiState<Zone>> = _zoneDetailState.asStateFlow()

    private var loadZonesJob: Job? = null

    init {
        loadZones()
    }

    fun loadZones() {
        loadZonesJob?.cancel()
        loadZonesJob = viewModelScope.launch {
            repository.getZones().collect { state ->
                _zonesState.value = state
            }
        }
    }

    fun loadZoneById(id: Long) {
        viewModelScope.launch {
            _zoneDetailState.value = UiState.Loading
            _zoneDetailState.value = repository.getZoneById(id)
        }
    }

    class Factory(private val repository: ZoneRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ZoneViewModel(repository) as T
        }
    }
}
