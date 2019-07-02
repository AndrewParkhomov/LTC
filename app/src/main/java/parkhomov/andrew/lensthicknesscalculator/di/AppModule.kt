package parkhomov.andrew.lensthicknesscalculator.di

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import parkhomov.andrew.activity.di.moduleActivity
import parkhomov.andrew.base.helper.NavigationI
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.diameter.di.moduleDiameter
import parkhomov.andrew.language.di.moduleLanguage
import parkhomov.andrew.lensthicknesscalculator.navigation.NavigationHandlerImpl
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.thickness.di.moduleThickness
import parkhomov.andrew.transposition.di.moduleTransposition


val appModule = module {

    single<NavigationI> { NavigationHandlerImpl() }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }
    single { Interactor(get()) }
}

// Gather all app modules
val modulesList = listOf(
        appModule,
        moduleActivity,
        navigationModule,
        moduleLanguage,
        moduleThickness,
        moduleDiameter,
        moduleTransposition
)
