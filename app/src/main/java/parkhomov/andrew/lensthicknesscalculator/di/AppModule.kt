package parkhomov.andrew.lensthicknesscalculator.di

import org.koin.dsl.module.module
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityI
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.DiameterI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.DiameterPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.GlossaryI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.GlossaryPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.ThicknessI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.ThicknessPresenter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.TranspositionI
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.TranspositionPresenter

/**
 * App Components
 */
val weatherAppModule = module {

    // Presenter with injection parameter for Diameter View
    factory<SingleActivityI.Presenter> { SingleActivityPresenter(get()) }

    factory<ThicknessI.Presenter> { ThicknessPresenter() }
    factory<DiameterI.Presenter> { DiameterPresenter() }
    factory<GlossaryI.Presenter> { GlossaryPresenter() }
    factory<TranspositionI.Presenter> { TranspositionPresenter() }

}

// Gather all app modules
val lensThicknessCalculatorApp = listOf(weatherAppModule, navigationModule)
