package parkhomov.andrew.activity.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.activity.viewmodel.ViewModelActivity
import parkhomov.andrew.activity.viewmodel.ViewModelActivityImpl

val moduleActivity = module {

    viewModel<ViewModelActivity> { ViewModelActivityImpl(get()) }

}