package parkhomov.andrew.lensthicknesscalculator.di


import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

private val cicerone: Cicerone<Router> = Cicerone.create()

val navigationModule = module {
    single { cicerone.router }
    single { cicerone.navigatorHolder }
}