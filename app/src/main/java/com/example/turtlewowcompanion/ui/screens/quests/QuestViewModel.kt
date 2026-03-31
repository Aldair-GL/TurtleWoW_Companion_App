package com.example.turtlewowcompanion.ui.screens.quests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.QuestRepository
import com.example.turtlewowcompanion.domain.model.Quest
import com.example.turtlewowcompanion.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestViewModel(private val repository: QuestRepository) : ViewModel() {

    private val _questsState = MutableStateFlow<UiState<List<Quest>>>(UiState.Loading)
    val questsState: StateFlow<UiState<List<Quest>>> = _questsState.asStateFlow()

    private val _questDetailState = MutableStateFlow<UiState<Quest>>(UiState.Loading)
    val questDetailState: StateFlow<UiState<Quest>> = _questDetailState.asStateFlow()

    init {
        loadQuests()
    }

    fun loadQuests() {
        viewModelScope.launch {
            repository.getQuests().collect { state ->
                _questsState.value = state
            }
        }
    }

    fun loadQuestById(id: Long) {
        viewModelScope.launch {
            _questDetailState.value = UiState.Loading
            _questDetailState.value = repository.getQuestById(id)
        }
    }

    class Factory(private val repository: QuestRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuestViewModel(repository) as T
        }
    }
}

