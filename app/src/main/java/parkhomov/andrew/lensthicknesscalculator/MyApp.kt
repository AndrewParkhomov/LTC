package parkhomov.andrew.lensthicknesscalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import parkhomov.andrew.lensthicknesscalculator.activity.MainActivityViewModel
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.view.diameter.DiameterViewModel
import parkhomov.andrew.lensthicknesscalculator.view.thickness.ThicknessViewModel
import parkhomov.andrew.lensthicknesscalculator.view.transposition.TranspositionViewModel
import parkhomov.andrew.lensthicknesscalculator.viewmodel.*


class MyApp : MultiDexApplication() {

    private val appModule = module {

        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        single<PreferencesHelper> { AppPreferencesHelper(get()) }
        single { Interactor() }

        viewModel{ MainActivityViewModel(get()) }
        viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get()) }
        viewModel { ThicknessViewModel(get()) }
        viewModel { DiameterViewModel(get()) }
        viewModel<ViewModelResult> { ViewModelResultImpl(get()) }
        viewModel<ViewModelCompareList> { ViewModelCompareListImpl(get()) }
        viewModel { TranspositionViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(this)
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
