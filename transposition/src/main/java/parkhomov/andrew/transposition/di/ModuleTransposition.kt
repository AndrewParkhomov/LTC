package parkhomov.andrew.transposition.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.transposition.usecase.UseCaseTransposition
import parkhomov.andrew.transposition.usecase.UseCaseTranspositionImpl
import parkhomov.andrew.transposition.viewmodel.ViewModelTransposition
import parkhomov.andrew.transposition.viewmodel.ViewModelTranspositionImpl

/**
 * DI for this module
 */
val moduleTransposition = module {

    single<UseCaseTransposition>(createOnStart= true) { UseCaseTranspositionImpl(get()) }
    factory("StateTransposition") { MediatorLiveData<ViewModelTransposition.State>() }
    viewModel<ViewModelTransposition> { ViewModelTranspositionImpl(get("StateTransposition"), get()) }
}

