package parkhomov.andrew.lensthicknesscalculator

import android.app.Application
import org.koin.android.ext.android.startKoin
import parkhomov.andrew.lensthicknesscalculator.di.lensThicknessCalculatorApp


/**
 * Created by MyPC on 28.07.2017.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG)
//            Timber.plant(object : Timber.DebugTree() {
//                override fun createStackElementTag(element: StackTraceElement): String {
//                    return super.createStackElementTag(element) + " line " + element.lineNumber
//                }
//            })
//
//        if (!BuildConfig.DEBUG)
//            Fabric.with(this, Crashlytics())
//
//
//        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
//                .setDefaultFontPath("raleway.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        )

        // start Koin context
        startKoin(this, lensThicknessCalculatorApp)
    }
}
