package parkhomov.andrew.transposition.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.transposition.domain.UseCaseTransposition
import parkhomov.andrew.transposition.domain.UseCaseTranspositionImpl
import parkhomov.andrew.transposition.viewmodel.ViewModeTransposition
import parkhomov.andrew.transposition.viewmodel.ViewModelTranspositionImpl

/**
 * DI for this module
 */
val moduleTransposition = module {

    viewModel<ViewModeTransposition> { ViewModelTranspositionImpl(get(), get()) }
    single<UseCaseTransposition> { UseCaseTranspositionImpl(get()) }
    factory(override = true) { MediatorLiveData<Any>() }
    factory { ViewModelTranspositionImpl(get(), get()) }
}
