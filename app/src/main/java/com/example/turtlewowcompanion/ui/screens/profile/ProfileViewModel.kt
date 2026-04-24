package com.example.turtlewowcompanion.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import com.example.turtlewowcompanion.data.local.entity.UserCharacterEntity
import com.example.turtlewowcompanion.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _characters = MutableStateFlow<List<UserCharacterEntity>>(emptyList())
    val characters: StateFlow<List<UserCharacterEntity>> = _characters.asStateFlow()

    private val _dungeonProgress = MutableStateFlow<List<DungeonProgressEntity>>(emptyList())
    val dungeonProgress: StateFlow<List<DungeonProgressEntity>> = _dungeonProgress.asStateFlow()

    private val _completedCount = MutableStateFlow(0)
    val completedCount: StateFlow<Int> = _completedCount.asStateFlow()

    fun loadForUser(userId: Long) {
        viewModelScope.launch {
            userRepository.observeCharacters(userId).collect { _characters.value = it }
        }
        viewModelScope.launch {
            userRepository.observeDungeonProgress(userId).collect { _dungeonProgress.value = it }
        }
        viewModelScope.launch {
            _completedCount.value = userRepository.getCompletedCount(userId)
        }
    }

    fun addCharacter(userId: Long, name: String, raceName: String, className: String, level: Int) {
        viewModelScope.launch {
            userRepository.addCharacter(userId, name, raceName, className, level)
        }
    }

    fun deleteCharacter(characterId: Long) {
        viewModelScope.launch {
            userRepository.deleteCharacter(characterId)
        }
    }

    fun toggleDungeon(userId: Long, zoneId: Long, zoneName: String) {
        viewModelScope.launch {
            userRepository.toggleDungeonCompletion(userId, zoneId, zoneName)
            _completedCount.value = userRepository.getCompletedCount(userId)
        }
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(userRepository) as T
    }
}

