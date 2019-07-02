package parkhomov.andrew.thickness.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.thickness.usecase.UseCaseThickness
import parkhomov.andrew.thickness.usecase.UseCaseThicknessImpl
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness
import parkhomov.andrew.thickness.viewmodel.ViewModelThicknessImpl

/**
 * DI for this module
 */
val moduleThickness = module {

    single<UseCaseThickness>(createdAtStart= true) { UseCaseThicknessImpl(get(), get()) }
    factory(named("StateThickness")) { MediatorLiveData<ViewModelThickness.State>() }
    viewModel<ViewModelThickness> { ViewModelThicknessImpl(get(named( "StateThickness")), get()) }
}
