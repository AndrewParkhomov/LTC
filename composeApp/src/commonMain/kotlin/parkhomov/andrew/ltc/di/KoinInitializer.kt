package parkhomov.andrew.ltc.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(viewModelsModule)
    }

fun initKoin() = initKoin {}
