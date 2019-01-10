package parkhomov.andrew.lensthicknesscalculator.di

import android.preference.PreferenceManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.language.domain.FetchMemesUseCase
import parkhomov.andrew.language.domain.FetchMemesUseCaseImpl
import parkhomov.andrew.language.viewmodel.ListViewModel
import parkhomov.andrew.language.viewmodel.ListViewModelImpl
import parkhomov.andrew.lensthicknesscalculator.ui.activity.SingleActivityViewModel
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result.ResultViewModel
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.DiameterViewModel
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.ThicknessViewModel
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.TranspositionViewModel
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper

/**
 * App Components
 */
val appModule = module {

    viewModel { SingleActivityViewModel(get()) }
    viewModel { ThicknessViewModel(get()) }
    viewModel { DiameterViewModel() }
    viewModel { TranspositionViewModel() }
    viewModel { ResultViewModel() }
    viewModel<ListViewModel> { ListViewModelImpl(get(), get()) }

    single(createOnStart = true) { Interactor(get()) }
    single(createOnStart = true) { MediatorLiveData<ListViewModel.State>() }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single<PreferencesHelper> { AppPreferencesHelper(get()) }

    factory<FetchMemesUseCase>{ FetchMemesUseCaseImpl(get()) }

}

// Gather all app modules
val lensThicknessCalculatorApp = listOf(appModule, navigationModule)
