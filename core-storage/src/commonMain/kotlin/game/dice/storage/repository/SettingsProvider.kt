package game.dice.storage.repository

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface SettingsProvider {
    suspend fun updateLanguage(value: String)
    suspend fun getLanguage(): String
    fun getLanguageFlow(): Flow<String>
    suspend fun updateTheme(value: Int)
    suspend fun getTheme(): Int?
    fun getThemeFlow(): Flow<Int?>
}
