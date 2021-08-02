package parkhomov.andrew.lensthicknesscalculator.di

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import parkhomov.andrew.activity.di.moduleActivity
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.base.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.comparelist.di.moduleCompareList
import parkhomov.andrew.diameter.di.moduleDiameter
import parkhomov.andrew.language.di.moduleLanguage
import parkhomov.andrew.result.di.moduleResult
import parkhomov.andrew.thickness.di.moduleThickness
import parkhomov.andrew.transposition.di.moduleTransposition
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

private val cicerone: Cicerone<Router> = Cicerone.create()

val appModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }
    single { Interactor(get()) }
    single { cicerone.router }
    single { cicerone.navigatorHolder }
}

// Gather all app modules
val modulesList = listOf(
        appModule,
        moduleActivity,
        moduleLanguage,
        moduleThickness,
        moduleDiameter,
        moduleResult,
        moduleCompareList,
        moduleTransposition
)
