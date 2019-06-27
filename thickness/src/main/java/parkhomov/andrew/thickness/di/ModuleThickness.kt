package parkhomov.andrew.thickness.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.thickness.usecase.UseCaseThickness
import parkhomov.andrew.thickness.usecase.UseCaseThicknessImpl
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness
import parkhomov.andrew.thickness.viewmodel.ViewModelThicknessImpl

/**
 * DI for this module
 */
val moduleThickness = module {

    single<UseCaseThickness>(createOnStart= true) { UseCaseThicknessImpl(get(), get()) }
    factory("StateThickness") { MediatorLiveData<ViewModelThickness.State>() }
    viewModel<ViewModelThickness> { ViewModelThicknessImpl(get( "StateThickness"), get()) }
}
