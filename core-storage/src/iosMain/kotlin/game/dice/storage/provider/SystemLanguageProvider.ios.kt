package game.dice.storage.provider

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

private class SystemLanguageProviderImpl : SystemLanguageProvider {
    override fun getLanguage(): String {
        val locale = NSLocale.currentLocale
        return locale.languageCode ?: "en"
    }
}

actual fun createSystemLanguageProvider(): SystemLanguageProvider = SystemLanguageProviderImpl()
