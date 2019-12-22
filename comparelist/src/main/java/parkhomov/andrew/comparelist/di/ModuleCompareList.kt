package parkhomov.andrew.comparelist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import parkhomov.andrew.comparelist.viewmodel.ViewModelCompareList
import parkhomov.andrew.comparelist.viewmodel.ViewModelCompareListImpl

/**
 * DI for this module
 */
val moduleCompareList = module {

    viewModel<ViewModelCompareList> { ViewModelCompareListImpl(get()) }
}
