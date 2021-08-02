package parkhomov.andrew.activity.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.activity.viewmodel.ViewModelActivity
import parkhomov.andrew.activity.viewmodel.ViewModelActivityImpl

val moduleActivity = module {

    viewModel<ViewModelActivity> { ViewModelActivityImpl(get()) }

}