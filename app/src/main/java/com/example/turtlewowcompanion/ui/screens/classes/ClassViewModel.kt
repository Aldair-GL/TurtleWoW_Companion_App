package com.example.turtlewowcompanion.ui.screens.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.WowClassRepository
import com.example.turtlewowcompanion.domain.model.WowClass
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClassViewModel(private val repository: WowClassRepository) : ViewModel() {

    private val _classesState = MutableStateFlow<UiState<List<WowClass>>>(UiState.Loading)
    val classesState: StateFlow<UiState<List<WowClass>>> = _classesState.asStateFlow()

    private val _classDetailState = MutableStateFlow<UiState<WowClass>>(UiState.Loading)
    val classDetailState: StateFlow<UiState<WowClass>> = _classDetailState.asStateFlow()

    init { loadClasses() }

    fun loadClasses() {
        viewModelScope.launch {
            repository.getClasses().collect { _classesState.value = it }
        }
    }

    fun loadClassById(id: Long) {
        viewModelScope.launch {
            _classDetailState.value = UiState.Loading
            _classDetailState.value = repository.getClassById(id)
        }
    }

    class Factory(private val repository: WowClassRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ClassViewModel(repository) as T
    }
}

