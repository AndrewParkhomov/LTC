package parkhomov.andrew.ltc.di

import game.dice.storage.di.storageModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(viewModelsModule, otherModule, storageModule, databaseModule)
    }

fun initKoin() = initKoin {}
