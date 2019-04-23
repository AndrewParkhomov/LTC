package parkhomov.andrew.language.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.language.domain.UseCaseLanguage
import parkhomov.andrew.language.domain.UseCaseLanguageImpl
import parkhomov.andrew.language.viewmodel.ViewModeLanguage
import parkhomov.andrew.language.viewmodel.ViewModelLanguageImpl

/**
 * DI for this module
 */
val moduleLanguage = module {

    viewModel<ViewModeLanguage> { ViewModelLanguageImpl(get(), get()) }
    single<UseCaseLanguage> { UseCaseLanguageImpl(get()) }
    factory(override = true) { MediatorLiveData<Any>() }
    factory { ViewModelLanguageImpl(get(), get()) }


}
