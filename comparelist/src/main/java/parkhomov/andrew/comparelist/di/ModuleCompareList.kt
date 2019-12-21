package parkhomov.andrew.comparelist.di

import androidx.lifecycle.MediatorLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import parkhomov.andrew.comparelist.viewmodel.ViewModelCompareList
import parkhomov.andrew.comparelist.viewmodel.ViewModelCompareListImpl

/**
 * DI for this module
 */
val moduleCompareList = module {

    factory(named("StateCompareList")) { MediatorLiveData<ViewModelCompareList.State>() }
    viewModel<ViewModelCompareList> { ViewModelCompareListImpl(get(named("StateCompareList")), get()) }
}
