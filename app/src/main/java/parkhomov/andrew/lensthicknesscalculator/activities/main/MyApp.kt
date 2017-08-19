package parkhomov.andrew.lensthicknesscalculator.activities.main

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.BuildConfig
import io.fabric.sdk.android.Fabric
import parkhomov.andrew.lensthicknesscalculator.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by MyPC on 28.07.2017.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        getAppContext = this
        if (!BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("raleway.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }


    companion object {
        lateinit var getAppContext: MyApp
            private set
    }
}
