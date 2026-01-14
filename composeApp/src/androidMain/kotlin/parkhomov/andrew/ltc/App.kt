package parkhomov.andrew.ltc

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import game.dice.storage.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.ltc.di.databaseModule
import parkhomov.andrew.ltc.di.otherModule
import parkhomov.andrew.ltc.di.viewModelsModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(viewModelsModule, otherModule, storageModule, databaseModule)
        }
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }
}
