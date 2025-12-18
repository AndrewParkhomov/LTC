package parkhomov.andrew.ltc.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import parkhomov.andrew.ltc.ui.compare.CompareLensViewModel
import parkhomov.andrew.ltc.ui.main.MainScreenViewModel

val viewModelsModule =
    module {
        viewModelOf(::MainScreenViewModel)
        viewModelOf(::CompareLensViewModel)
    }
