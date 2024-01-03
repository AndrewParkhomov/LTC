package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.MobileAds
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import parkhomov.andrew.lensthicknesscalculator.domain.Interactor
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_THEME
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.view.compare.CompareListViewModel
import parkhomov.andrew.lensthicknesscalculator.view.result.ResultViewModel
import parkhomov.andrew.lensthicknesscalculator.view.thickness.ThicknessViewModel


class MyApp : Application() {

    private val sp: AppPreferences by inject()

    private val appModule = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        single { AppPreferences(get()) }
        single { Interactor() }
        viewModel { ThicknessViewModel(get()) }
        viewModel { CompareListViewModel(get()) }
        viewModel { ResultViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
        startKoin {
            androidContext(this@MyApp)
            androidLogger(Level.ERROR)
            modules(appModule)
        }

        val theme = sp.getIntValue(APP_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(theme)
        MobileAds.initialize(this)
    }
}
