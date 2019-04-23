package parkhomov.andrew.lensthicknesscalculator.di

import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.diameter.di.moduleDiameter
import parkhomov.andrew.language.di.moduleLanguage
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityViewModel
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.thickness.di.moduleThickness
import parkhomov.andrew.transposition.di.moduleTransposition

/**
 * App Components
 */
val appModule = module {

    viewModel { SingleActivityViewModel(get()) }

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }

}

// Gather all app modules
val lensThicknessCalculatorApp = listOf(
        appModule,
        navigationModule,
        moduleLanguage,
        moduleThickness,
        moduleDiameter,
        moduleTransposition
)
