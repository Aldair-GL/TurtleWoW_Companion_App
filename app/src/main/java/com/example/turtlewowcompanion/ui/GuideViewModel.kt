package com.example.turtlewowcompanion.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.GuideCategory
import com.example.turtlewowcompanion.data.GuideRepository
import com.example.turtlewowcompanion.data.local.AppDatabase
import com.example.turtlewowcompanion.data.local.GuideEntryEntity
import com.example.turtlewowcompanion.data.remote.NetworkModule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuideViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GuideRepository(
        guideDao = AppDatabase.getInstance(application).guideDao(),
        api = NetworkModule.api
    )

    private val _selectedCategory = kotlinx.coroutines.flow.MutableStateFlow(GuideCategory.OBJECTS)
    val selectedCategory: StateFlow<GuideCategory> = _selectedCategory

    val entries: StateFlow<List<GuideEntryEntity>> = selectedCategory
        .flatMapLatest { category -> repository.observeByCategory(category) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        GuideCategory.entries.forEach { category ->
            viewModelScope.launch {
                repository.refresh(category)
            }
        }
    }

    fun onCategorySelected(category: GuideCategory) {
        _selectedCategory.update { category }
        viewModelScope.launch {
            repository.refresh(category)
        }
    }
}
