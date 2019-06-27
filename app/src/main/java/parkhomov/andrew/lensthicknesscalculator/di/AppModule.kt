package parkhomov.andrew.lensthicknesscalculator.di

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import parkhomov.andrew.activity.di.moduleActivity
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.diameter.di.moduleDiameter
import parkhomov.andrew.language.di.moduleLanguage
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.thickness.di.moduleThickness
import parkhomov.andrew.transposition.di.moduleTransposition


val appModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }
    single { Interactor(get()) }
}

// Gather all app modules
val lensThicknessCalculatorApp = listOf(
        appModule,
        moduleActivity,
        navigationModule,
        moduleLanguage,
        moduleThickness,
        moduleDiameter,
        moduleTransposition
)
