package parkhomov.andrew.result.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.result.viewmodel.ViewModelResult
import parkhomov.andrew.result.viewmodel.ViewModelResultImpl


val moduleResult = module {

    viewModel<ViewModelResult> { ViewModelResultImpl(get()) }
}