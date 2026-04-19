package com.example.turtlewowcompanion.ui.screens.bosses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.BossRepository
import com.example.turtlewowcompanion.domain.model.Boss
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BossViewModel(private val repository: BossRepository) : ViewModel() {

    private val _bossesState = MutableStateFlow<UiState<List<Boss>>>(UiState.Loading)
    val bossesState: StateFlow<UiState<List<Boss>>> = _bossesState.asStateFlow()

    private val _bossDetailState = MutableStateFlow<UiState<Boss>>(UiState.Loading)
    val bossDetailState: StateFlow<UiState<Boss>> = _bossDetailState.asStateFlow()

    private var loadJob: Job? = null

    fun loadZoneBosses(zoneId: Long) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            repository.getZoneBosses(zoneId).collect { _bossesState.value = it }
        }
    }

    fun loadBossById(id: Long) {
        viewModelScope.launch {
            _bossDetailState.value = UiState.Loading
            _bossDetailState.value = repository.getBossById(id)
        }
    }

    class Factory(private val repository: BossRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = BossViewModel(repository) as T
    }
}

