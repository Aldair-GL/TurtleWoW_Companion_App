package com.example.turtlewowcompanion.ui.common

/** Estado genérico para operaciones de carga de datos. */
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

