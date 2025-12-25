package parkhomov.andrew.ltc

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.ltc.di.otherModule
import parkhomov.andrew.ltc.di.viewModelsModule
import game.dice.storage.di.storageModule
import game.dice.storage.repository.SettingsProvider
import java.util.Locale


class App : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(viewModelsModule, otherModule, storageModule)
        }
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
        setAppLanguage()
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
