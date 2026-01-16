package game.dice.storage.provider

/**
 * Provides access to system language detection.
 */
interface SystemLanguageProvider {
    /**
     * Returns the current system language code (e.g., "en", "uk", "pt", "hi").
     */
    fun getLanguage(): String
}

/**
 * Platform-specific implementation of SystemLanguageProvider.
 */
expect fun createSystemLanguageProvider(): SystemLanguageProvider
