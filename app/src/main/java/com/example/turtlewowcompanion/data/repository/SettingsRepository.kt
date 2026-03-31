package com.example.turtlewowcompanion.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property para crear el DataStore como singleton
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
        val ONBOARDING_COMPLETE_KEY = booleanPreferencesKey("onboarding_complete")
    }

    val isDarkTheme: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[DARK_THEME_KEY] ?: true  // Por defecto tema oscuro (WoW style)
    }

    val isOnboardingComplete: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[ONBOARDING_COMPLETE_KEY] ?: false
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }

    suspend fun setOnboardingComplete(complete: Boolean) {
        dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETE_KEY] = complete
        }
    }
}

