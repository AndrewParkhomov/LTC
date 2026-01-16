package game.dice.storage.di

import game.dice.storage.DataStoreProvider
import game.dice.storage.provider.SystemLanguageProvider
import game.dice.storage.provider.createSystemLanguageProvider
import game.dice.storage.repository.SettingsProvider
import game.dice.storage.repository.SettingsProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageModule =
    module {
        singleOf(::DataStoreProvider)
        singleOf(DataStoreProvider::get)
        singleOf(::SettingsProviderImpl) bind SettingsProvider::class
        single<SystemLanguageProvider> { createSystemLanguageProvider() }
    }
