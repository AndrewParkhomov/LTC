package game.dice.storage.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import game.dice.storage.provider.SystemLanguageProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsProviderImpl(
    private val dataStore: DataStore<Preferences>,
    private val systemLanguageProvider: SystemLanguageProvider,
) : SettingsProvider {

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[key] = value
        }
    }

    private suspend fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): T {
        return dataStore.data.map { preferences: Preferences ->
            preferences[key] ?: defaultValue
        }.first()
    }

    override suspend fun updateTheme(value: Int) = setValue(APP_THEME, value)

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

    override suspend fun updateLanguage(value: String) = setValue(APP_LANGUAGE, value)

    override fun getLanguageFlow(): Flow<String> {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_LANGUAGE] ?: systemLanguageProvider.getLanguage()
        }
    }

    override suspend fun getLanguage(): String {
        return dataStore.data.map { preferences: Preferences ->
            preferences[APP_LANGUAGE] ?: systemLanguageProvider.getLanguage()
        }.first()
    }

    override suspend fun setIosPromoShown() = setValue(IOS_PROMO_SHOWN, true)

    override suspend fun isIosPromoShown(): Boolean = getValue(IOS_PROMO_SHOWN, false)

    override suspend fun incrementCalculationCount() {
        dataStore.edit { preferences: MutablePreferences ->
            val currentCount = preferences[CALCULATION_COUNT] ?: 0
            preferences[CALCULATION_COUNT] = currentCount + 1
        }
    }

    override suspend fun getCalculationCount(): Int = getValue(CALCULATION_COUNT, 0)

    companion object {
        val APP_LANGUAGE: Preferences.Key<String> = stringPreferencesKey("APP_LANGUAGE")
        val APP_THEME: Preferences.Key<Int> = intPreferencesKey("APP_THEME")
        val IOS_PROMO_SHOWN: Preferences.Key<Boolean> = booleanPreferencesKey("IOS_PROMO_SHOWN")
        val CALCULATION_COUNT: Preferences.Key<Int> = intPreferencesKey("CALCULATION_COUNT")
    }
}
