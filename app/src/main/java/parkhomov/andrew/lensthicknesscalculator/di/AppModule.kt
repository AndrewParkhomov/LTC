package parkhomov.andrew.lensthicknesscalculator.di

import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityI
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language.LanguageI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language.LanguagePresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result.ResultI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result.ResultPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.DiameterI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.DiameterPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.GlossaryI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.GlossaryPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.ThicknessI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.ThicknessPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.TranspositionI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.TranspositionPresenter
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.PreferencesHelper

/**
 * App Components
 */
val weatherAppModule = module {

    // Presenter with injection parameter for Diameter View
    factory<SingleActivityI.Presenter> { SingleActivityPresenter(get(), get()) }

    factory<ThicknessI.Presenter> { ThicknessPresenter(get(), get()) }
    factory<DiameterI.Presenter> { DiameterPresenter() }
    factory<GlossaryI.Presenter> { GlossaryPresenter() }
    factory<TranspositionI.Presenter> { TranspositionPresenter() }
    factory<ResultI.Presenter> { ResultPresenter() }
    factory<LanguageI.Presenter> { LanguagePresenter(get()) }

    single(createOnStart = true) { Interactor(get()) }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }


}

// Gather all app modules
val lensThicknessCalculatorApp = listOf(weatherAppModule, navigationModule)
