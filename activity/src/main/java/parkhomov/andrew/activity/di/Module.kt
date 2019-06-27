package parkhomov.andrew.activity.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.activity.usecase.UseCaseActivity
import parkhomov.andrew.activity.usecase.UseCaseActivityImpl
import parkhomov.andrew.activity.viewmodel.ViewModelActivity
import parkhomov.andrew.activity.viewmodel.ViewModelActivityImpl

val moduleActivity = module {

    single<UseCaseActivity>(createOnStart= true) { UseCaseActivityImpl(get()) }
    factory(name = "StateActivity") { MediatorLiveData<ViewModelActivity.State>() }
    viewModel<ViewModelActivity> { ViewModelActivityImpl(get("StateActivity"), get()) }

}