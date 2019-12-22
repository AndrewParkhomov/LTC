package parkhomov.andrew.diameter.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameter
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameterImpl

/**
 * DI for this module
 */
val moduleDiameter = module {

    viewModel<ViewModelDiameter> { ViewModelDiameterImpl() }
}
