package com.example.turtlewowcompanion.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.turtlewowcompanion.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val loggedInUserId: Long? = null,
    val loggedInUsername: String? = null,
    val errorMessage: String? = null,
    val isRegisterMode: Boolean = false
)

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = userRepository.login(username, password)
            result.fold(
                onSuccess = { user ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loggedInUserId = user.id,
                        loggedInUsername = user.username
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message)
                }
            )
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = userRepository.register(username, password)
            result.fold(
                onSuccess = { userId ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loggedInUserId = userId,
                        loggedInUsername = username.trim()
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message)
                }
            )
        }
    }

    fun toggleMode() {
        _state.value = _state.value.copy(
            isRegisterMode = !_state.value.isRegisterMode,
            errorMessage = null
        )
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    fun logout() {
        _state.value = AuthUiState()
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = AuthViewModel(userRepository) as T
    }
}

