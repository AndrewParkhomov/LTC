package parkhomov.andrew.lensthicknesscalculator

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import parkhomov.andrew.lensthicknesscalculator.di.modulesList


class MyApp : MultiDexApplication() {

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
            FirebaseAnalytics.getInstance(this)
        }
        startKoin {
            androidContext(this@MyApp)
            modules(modulesList)
        }
    }

    companion object {
        // for pre lollipop
        // allow to use drawableLeft/Right
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
