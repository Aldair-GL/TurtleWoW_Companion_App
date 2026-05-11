package com.example.turtlewowcompanion.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.local.entity.BossKillProgressEntity
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import com.example.turtlewowcompanion.data.local.entity.LootProgressEntity
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

    private val _bossKills = MutableStateFlow<List<BossKillProgressEntity>>(emptyList())
    val bossKills: StateFlow<List<BossKillProgressEntity>> = _bossKills.asStateFlow()

    private val _loot = MutableStateFlow<List<LootProgressEntity>>(emptyList())
    val loot: StateFlow<List<LootProgressEntity>> = _loot.asStateFlow()

    private val _completedDungeons = MutableStateFlow(0)
    val completedDungeons: StateFlow<Int> = _completedDungeons.asStateFlow()

    private val _bossKillCount = MutableStateFlow(0)
    val bossKillCount: StateFlow<Int> = _bossKillCount.asStateFlow()

    private val _lootCount = MutableStateFlow(0)
    val lootCount: StateFlow<Int> = _lootCount.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val maxCharacters: Int = UserRepository.MAX_CHARACTERS_PER_USER

    fun loadForUser(userId: Long) {
        viewModelScope.launch {
            userRepository.observeCharacters(userId).collect { _characters.value = it }
        }
        viewModelScope.launch {
            userRepository.observeDungeonProgress(userId).collect {
                _dungeonProgress.value = it
                _completedDungeons.value = it.count { d -> d.completed }
            }
        }
        viewModelScope.launch {
            userRepository.observeBossKills(userId).collect {
                _bossKills.value = it
                _bossKillCount.value = it.size
            }
        }
        viewModelScope.launch {
            userRepository.observeLoot(userId).collect {
                _loot.value = it
                _lootCount.value = it.size
            }
        }
    }

    fun addCharacter(userId: Long, name: String, raceName: String, className: String, level: Int) {
        viewModelScope.launch {
            val result = userRepository.addCharacter(userId, name, raceName, className, level)
            result.onFailure { _errorMessage.value = it.message }
        }
    }

    fun deleteCharacter(userId: Long, characterId: Long) {
        viewModelScope.launch { userRepository.deleteCharacter(userId, characterId) }
    }

    fun unmarkDungeon(userId: Long, zoneId: Long, zoneName: String) {
        viewModelScope.launch { userRepository.toggleDungeonCompletion(userId, zoneId, zoneName) }
    }

    fun unmarkBoss(userId: Long, bossId: Long, bossName: String, zoneName: String) {
        viewModelScope.launch { userRepository.toggleBossKill(userId, bossId, bossName, zoneName) }
    }

    fun clearError() { _errorMessage.value = null }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(userRepository) as T
    }
}



