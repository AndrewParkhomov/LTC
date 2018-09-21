package parkhomov.andrew.lensthicknesscalculator.di


import org.koin.dsl.module.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

val navigationModule = module(createOnStart = true) {
    // provided router
    single { provideRouter() }
    // Fill property
    single { provideNavigatorHolder() }
}


private val cicerone: Cicerone<Router> = Cicerone.create()

fun provideRouter(): Router {
    return cicerone.router
}

fun provideNavigatorHolder(): NavigatorHolder {
    return cicerone.navigatorHolder
}