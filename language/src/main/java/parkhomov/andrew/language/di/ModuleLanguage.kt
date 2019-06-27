package parkhomov.andrew.language.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.language.usecase.UseCaseLanguage
import parkhomov.andrew.language.usecase.UseCaseLanguageImpl
import parkhomov.andrew.language.viewmodel.ViewModelLanguage
import parkhomov.andrew.language.viewmodel.ViewModelLanguageImpl

/**
 * DI for this module
 */
val moduleLanguage = module {

    single<UseCaseLanguage> { UseCaseLanguageImpl(get()) }
    factory("StateLanguage") { MediatorLiveData<ViewModelLanguage.State>() }
    viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get("StateLanguage"), get()) }

}
