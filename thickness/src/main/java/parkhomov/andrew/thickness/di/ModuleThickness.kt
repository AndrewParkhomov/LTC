package parkhomov.andrew.thickness.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness
import parkhomov.andrew.thickness.viewmodel.ViewModelThicknessImpl

val moduleThickness = module {

    factory(named("StateThickness")) { MediatorLiveData<ViewModelThickness.State>() }
    viewModel<ViewModelThickness> { ViewModelThicknessImpl(get(named( "StateThickness")), get()) }
}
