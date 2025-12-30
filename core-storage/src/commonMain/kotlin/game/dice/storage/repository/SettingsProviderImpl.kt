package game.dice.storage.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsProviderImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsProvider {

    override suspend fun updateTheme(value: Int) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[APP_THEME] = value
        }
    }

    override suspend fun getTheme(): Int? {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_THEME]
        }.first()
    }

    override fun getThemeFlow(): Flow<Int?> {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_THEME]
        }
    }

    override suspend fun updateLanguage(value: String) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[APP_LANGUAGE] = value
        }
    }

    override fun getLanguageFlow(): Flow<String> {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_LANGUAGE] ?: DEFAULT_LANGUAGE
        }
    }

    override suspend fun getLanguage(): String {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_LANGUAGE] ?: DEFAULT_LANGUAGE
        }.first()
    }

    companion object {
        val APP_LANGUAGE: Preferences.Key<String> = stringPreferencesKey("APP_LANGUAGE")
        val APP_THEME: Preferences.Key<Int> = intPreferencesKey("APP_THEME")
        private const val DEFAULT_LANGUAGE: String = "en"
    }
}
