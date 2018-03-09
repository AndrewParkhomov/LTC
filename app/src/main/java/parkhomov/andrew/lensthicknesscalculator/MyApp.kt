package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by MyPC on 28.07.2017.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        getAppContext = this

        if (BuildConfig.DEBUG)
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + " line " + element.lineNumber
                }
            })

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
