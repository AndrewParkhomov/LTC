package parkhomov.andrew.language.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.language.viewmodel.ViewModelLanguage
import parkhomov.andrew.language.viewmodel.ViewModelLanguageImpl

val moduleLanguage = module {

    factory(named("StateLanguage")) { MediatorLiveData<ViewModelLanguage.State>() }
    viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get(named("StateLanguage")), get()) }


}
