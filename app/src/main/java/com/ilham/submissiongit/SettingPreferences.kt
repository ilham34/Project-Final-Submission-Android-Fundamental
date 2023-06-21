package com.ilham.submissiongit

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences(context: Context) {
    private val settingDataStore = context.prefDataStore
    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> = settingDataStore.data.map { prefereces ->
        prefereces[themeKey] ?: false
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        settingDataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }
}