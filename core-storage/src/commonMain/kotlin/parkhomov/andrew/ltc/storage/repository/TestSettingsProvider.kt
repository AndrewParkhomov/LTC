package parkhomov.andrew.ltc.storage.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestSettingsProvider : SettingsProvider {
    override suspend fun updateLanguage(value: String) {

    }

    override suspend fun getLanguage(): String {
        return ""
    }

    override suspend fun getLanguageFlow(): Flow<String> {
        return flowOf("")
    }

    override suspend fun updateTheme(value: Int) {

    }

    override suspend fun getTheme(): Int? {
        return null
    }

    override suspend fun getThemeFlow(): Flow<Int?> {
       return flowOf(null)
    }

}