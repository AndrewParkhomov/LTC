package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import parkhomov.andrew.lensthicknesscalculator.di.modulesList

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }

        startKoin {
            androidContext(this@MyApp)
            modules(modulesList)
        }

        Fabric.with(this, Crashlytics())
        FirebaseAnalytics.getInstance(this)
    }

    companion object {
        // for pre lollipop
        // allow to use drawableLeft/Right
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
