package game.dice.storage.provider

import java.util.Locale

private class SystemLanguageProviderImpl : SystemLanguageProvider {
    override fun getLanguage(): String = Locale.getDefault().language
}

actual fun createSystemLanguageProvider(): SystemLanguageProvider = SystemLanguageProviderImpl()
