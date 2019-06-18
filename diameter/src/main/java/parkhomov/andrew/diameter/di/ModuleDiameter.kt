package parkhomov.andrew.diameter.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.diameter.domain.UseCaseDiameter
import parkhomov.andrew.diameter.domain.UseCaseDiameterImpl
import parkhomov.andrew.diameter.viewmodel.ViewModeDiameter
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameterImpl

/**
 * DI for this module
 */
val moduleDiameter = module {

    single<UseCaseDiameter> { UseCaseDiameterImpl(get()) }
    factory("StateDiameter") { MediatorLiveData<ViewModeDiameter.State>() }
    viewModel<ViewModeDiameter> { ViewModelDiameterImpl(get("StateDiameter"), get()) }
}
