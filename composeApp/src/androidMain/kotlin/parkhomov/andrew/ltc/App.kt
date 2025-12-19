package parkhomov.andrew.ltc

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.ltc.di.otherModule
import parkhomov.andrew.ltc.di.viewModelsModule
import parkhomov.andrew.ltc.domain.CompareLensStorageOld
import parkhomov.andrew.ltc.domain.CompareLensStorageOldImpl
import parkhomov.andrew.ltc.preferences.APP_THEME
import parkhomov.andrew.ltc.preferences.AppPreferences
import parkhomov.andrew.ltc.preferences.AppPreferencesImpl
import parkhomov.andrew.ltc.storage.di.storageModule
import parkhomov.andrew.ltc.storage.repository.SettingsProvider
import parkhomov.andrew.ltc.view.compare.CompareListViewModel
import parkhomov.andrew.ltc.view.result.ResultViewModel
import parkhomov.andrew.ltc.view.thickness.ThicknessViewModel
import java.util.Locale


class App : Application() {

    private val appModule = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        singleOf(::AppPreferencesImpl) bind AppPreferences::class
        singleOf(::CompareLensStorageOldImpl) bind CompareLensStorageOld::class
        viewModelOf(::ThicknessViewModel)
        viewModelOf(::CompareListViewModel)
        viewModelOf(::ResultViewModel)
    }

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(appModule, viewModelsModule, otherModule, storageModule)
        }
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
        setAppLanguage()

        val sp: AppPreferences = get()
        val theme = sp.getIntValue(APP_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    private fun setAppLanguage() {
        val settingsProvider: SettingsProvider = get()
        applicationScope.launch {
            settingsProvider.getLanguageFlow()
                .collect(::applyLanguage)
        }
    }

    private fun applyLanguage(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}
