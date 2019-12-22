package parkhomov.andrew.transposition.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.transposition.viewmodel.ViewModelTransposition
import parkhomov.andrew.transposition.viewmodel.ViewModelTranspositionImpl

/**
 * DI for this module
 */
val moduleTransposition = module {

    viewModel<ViewModelTransposition> { ViewModelTranspositionImpl() }
}

