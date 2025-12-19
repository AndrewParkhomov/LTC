package parkhomov.andrew.ltc.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import parkhomov.andrew.ltc.storage.di.storageModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(viewModelsModule, otherModule, storageModule)
    }

fun initKoin() = initKoin {}
