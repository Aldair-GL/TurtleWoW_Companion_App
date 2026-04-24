package com.example.turtlewowcompanion.ui.screens.professions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.ProfessionRepository
import com.example.turtlewowcompanion.domain.model.Profession
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfessionViewModel(private val repository: ProfessionRepository) : ViewModel() {

    private val _listState = MutableStateFlow<UiState<List<Profession>>>(UiState.Loading)
    val listState: StateFlow<UiState<List<Profession>>> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow<UiState<Profession>>(UiState.Loading)
    val detailState: StateFlow<UiState<Profession>> = _detailState.asStateFlow()

    init { loadProfessions() }

    fun loadProfessions() {
        viewModelScope.launch {
            repository.getProfessions().collect { _listState.value = it }
        }
    }

    fun loadProfessionById(id: Long) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            _detailState.value = repository.getProfessionById(id)
        }
    }

    class Factory(private val repository: ProfessionRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ProfessionViewModel(repository) as T
    }
}

