package com.example.turtlewowcompanion.ui.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.ItemRepository
import com.example.turtlewowcompanion.domain.model.Item
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _listState = MutableStateFlow<UiState<List<Item>>>(UiState.Loading)
    val listState: StateFlow<UiState<List<Item>>> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow<UiState<Item>>(UiState.Loading)
    val detailState: StateFlow<UiState<Item>> = _detailState.asStateFlow()

    private val _bossItemsState = MutableStateFlow<UiState<List<Item>>>(UiState.Loading)
    val bossItemsState: StateFlow<UiState<List<Item>>> = _bossItemsState.asStateFlow()

    private var loadJob: Job? = null

    init { loadItems() }

    fun loadItems() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            repository.getItems().collect { _listState.value = it }
        }
    }

    fun loadItemById(id: Long) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            _detailState.value = repository.getItemById(id)
        }
    }

    fun loadItemsByBoss(bossId: Long) {
        viewModelScope.launch {
            repository.getItemsByBoss(bossId).collect { _bossItemsState.value = it }
        }
    }

    class Factory(private val repository: ItemRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ItemViewModel(repository) as T
    }
}

