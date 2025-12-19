package parkhomov.andrew.ltc.storage.di

import parkhomov.andrew.ltc.storage.DataStoreProvider
import parkhomov.andrew.ltc.storage.repository.SettingsProvider
import parkhomov.andrew.ltc.storage.repository.SettingsProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageModule =
    module {
        singleOf(::DataStoreProvider)
        singleOf(DataStoreProvider::get)
        singleOf(::SettingsProviderImpl) bind SettingsProvider::class
    }
