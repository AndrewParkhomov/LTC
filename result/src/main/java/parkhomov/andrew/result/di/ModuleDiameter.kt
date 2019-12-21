package parkhomov.andrew.result.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.result.viewmodel.ViewModelResult
import parkhomov.andrew.result.viewmodel.ViewModelResultImpl


val moduleResult = module {

    factory(named("StateResult")) { MediatorLiveData<ViewModelResult.State>() }
    viewModel<ViewModelResult> { ViewModelResultImpl(get(named("StateResult")), get()) }
}