package parkhomov.andrew.language.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.language.usecase.UseCaseLanguage
import parkhomov.andrew.language.usecase.UseCaseLanguageImpl
import parkhomov.andrew.language.viewmodel.ViewModelLanguage
import parkhomov.andrew.language.viewmodel.ViewModelLanguageImpl

/**
 * DI for this module
 */
val moduleLanguage = module {

    single<UseCaseLanguage> { UseCaseLanguageImpl(get()) }
    factory(named("StateLanguage")) { MediatorLiveData<ViewModelLanguage.State>() }
    viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get(named("StateLanguage")), get()) }


}
