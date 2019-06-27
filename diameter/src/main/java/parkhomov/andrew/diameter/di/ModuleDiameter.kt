package parkhomov.andrew.diameter.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.diameter.usecase.UseCaseDiameter
import parkhomov.andrew.diameter.usecase.UseCaseDiameterImpl
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameter
import parkhomov.andrew.diameter.viewmodel.ViewModelDiameterImpl

/**
 * DI for this module
 */
val moduleDiameter = module {

    single<UseCaseDiameter>(createOnStart= true) { UseCaseDiameterImpl(get()) }
    factory("StateDiameter") { MediatorLiveData<ViewModelDiameter.State>() }
    viewModel<ViewModelDiameter> { ViewModelDiameterImpl(get("StateDiameter"), get()) }
}
