package parkhomov.andrew.language.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.language.viewmodel.ViewModelLanguage
import parkhomov.andrew.language.viewmodel.ViewModelLanguageImpl

val moduleLanguage = module {

    viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get()) }


}
