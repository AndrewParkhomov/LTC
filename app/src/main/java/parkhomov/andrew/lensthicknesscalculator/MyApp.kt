package parkhomov.andrew.lensthicknesscalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import parkhomov.andrew.lensthicknesscalculator.di.modulesList


class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(this)
        }
        startKoin {
            androidContext(this@MyApp)
            modules(modulesList)
        }
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
