package parkhomov.andrew.thickness.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.base.interactor.Interactor
import parkhomov.andrew.thickness.domain.UseCaseThickness
import parkhomov.andrew.thickness.domain.UseCaseThicknessImpl
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness
import parkhomov.andrew.thickness.viewmodel.ViewModelThicknessImpl

/**
 * DI for this module
 */
val moduleThickness = module {

    viewModel<ViewModelThickness> { ViewModelThicknessImpl(get(), get()) }
    single<UseCaseThickness> { UseCaseThicknessImpl(get()) }
    factory(override = true) { MediatorLiveData<Any>() }
    factory { ViewModelThicknessImpl(get(), get()) }
    factory { Interactor(get()) }

}
