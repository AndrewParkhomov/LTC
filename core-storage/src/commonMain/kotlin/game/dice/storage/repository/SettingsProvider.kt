package game.dice.storage.repository

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface SettingsProvider {
    suspend fun updateLanguage(value: String)
    suspend fun getLanguage(): String
    suspend fun getLanguageFlow(): Flow<String>
    suspend fun updateTheme(value: Int)
    suspend fun getTheme(): Int?
    suspend fun getThemeFlow(): Flow<Int?>
}
