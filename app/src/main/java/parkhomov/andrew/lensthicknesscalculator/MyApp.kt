package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import parkhomov.andrew.lensthicknesscalculator.di.lensThicknessCalculatorApp

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }

//        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
//                .setDefaultFontPath("raleway.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        )

        // start Koin context
        startKoin(this, lensThicknessCalculatorApp)
    }

    companion object {
        // for pre lollipop
        // allow to use drawableLeft/Right
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
