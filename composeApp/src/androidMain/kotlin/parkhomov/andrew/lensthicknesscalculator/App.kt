package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import parkhomov.andrew.lensthicknesscalculator.domain.CompareLensStorage
import parkhomov.andrew.lensthicknesscalculator.domain.CompareLensStorageImpl
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_THEME
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferencesImpl
import parkhomov.andrew.lensthicknesscalculator.view.compare.CompareListViewModel
import parkhomov.andrew.lensthicknesscalculator.view.result.ResultViewModel
import parkhomov.andrew.lensthicknesscalculator.view.thickness.ThicknessViewModel


class App : Application() {

    private val appModule = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        singleOf(::AppPreferencesImpl) bind AppPreferences::class
        singleOf(::CompareLensStorageImpl) bind CompareLensStorage::class
        viewModelOf(::ThicknessViewModel)
        viewModelOf(::CompareListViewModel)
        viewModelOf(::ResultViewModel)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(appModule)
        }
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG

//        val sp: AppPreferences = get()
//        val theme = sp.getIntValue(APP_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//        AppCompatDelegate.setDefaultNightMode(theme)
    }
}
