package com.example.turtlewowcompanion.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.SearchRepository
import com.example.turtlewowcompanion.domain.model.SearchResult
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _searchState = MutableStateFlow<UiState<List<SearchResult>>?>(null)
    val searchState: StateFlow<UiState<List<SearchResult>>?> = _searchState.asStateFlow()

    val searchHistory: StateFlow<List<String>> = repository.observeSearchHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        val q = _query.value.trim()
        if (q.isBlank()) return
        viewModelScope.launch {
            _searchState.value = UiState.Loading
            _searchState.value = repository.search(q)
        }
    }

    fun clearHistory() {
        viewModelScope.launch { repository.clearHistory() }
    }

    class Factory(private val repository: SearchRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(repository) as T
        }
    }
}

