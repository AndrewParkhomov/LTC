package parkhomov.andrew.diameter.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.diameter.usecase.UseCaseDiameter
import parkhomov.andrew.diameter.usecase.UseCaseDiameterImpl
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameter
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameterImpl

/**
 * DI for this module
 */
val moduleDiameter = module {

    single<UseCaseDiameter>(createdAtStart= true) { UseCaseDiameterImpl(get()) }
    factory(named("StateDiameter")) { MediatorLiveData<ViewModelDiameter.State>() }
    viewModel<ViewModelDiameter> { ViewModelDiameterImpl(get(named("StateDiameter")), get()) }
}
