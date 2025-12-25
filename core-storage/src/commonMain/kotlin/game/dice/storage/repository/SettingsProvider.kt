package game.dice.storage.repository

import kotlinx.coroutines.flow.Flow

interface SettingsProvider {
    suspend fun updateLanguage(value: String)
    suspend fun getLanguage(): String
    suspend fun getLanguageFlow(): Flow<String>
    suspend fun updateTheme(value: Int)
    suspend fun getTheme(): Int?
    suspend fun getThemeFlow(): Flow<Int?>
}
