package parkhomov.andrew.lensthicknesscalculator.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.crashlytics.android.Crashlytics

import io.fabric.sdk.android.Fabric
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.CONSTANT
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.*

/**
 * Created by MyPC on 28.07.2017.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        getAppContext = this
        if (!BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())

        setUpLanguage()
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

    private fun setUpLanguage() {
        val savedLanguage: SharedPreferences = getSharedPreferences (CONSTANT.SAVE_LANGUAGE, Context.MODE_PRIVATE)
        val locale = Locale(savedLanguage.getString(CONSTANT.SAVE_LANGUAGE, Utils.getCurrentLanguage()))
        val config = resources.configuration
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
