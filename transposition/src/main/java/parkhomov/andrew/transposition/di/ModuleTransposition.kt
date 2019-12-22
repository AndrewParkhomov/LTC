package parkhomov.andrew.transposition.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.transposition.viewmodel.ViewModelTransposition
import parkhomov.andrew.transposition.viewmodel.ViewModelTranspositionImpl

/**
 * DI for this module
 */
val moduleTransposition = module {

    factory(named("StateTransposition")) { MediatorLiveData<ViewModelTransposition.State>() }
    viewModel<ViewModelTransposition> { ViewModelTranspositionImpl(get(named("StateTransposition"))) }
}

