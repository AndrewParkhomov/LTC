package parkhomov.andrew.lensthicknesscalculator.utils

/**
 * Created by Andrew on 28.10.2017.
 */

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

class MyContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {

        fun wrap(context: Context, language: String): ContextWrapper {
            var tempContext = context
            val config = tempContext.resources.configuration

            val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getSystemLocale(config)
            } else {
                getSystemLocaleLegacy(config)
            }

            val country = when (language) {
                "uk" -> "uk_UA"
                "ru" -> "RU"
                else -> "en_US"
            }

            if (language != "" && sysLocale.language != language) {
                val locale = Locale(language,country)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    tempContext = tempContext.createConfigurationContext(config)
                } else {
                    tempContext.resources.updateConfiguration(config, tempContext.resources.displayMetrics)
                }
            }
            return MyContextWrapper(tempContext)
        }

        private fun getSystemLocaleLegacy(config: Configuration): Locale {
            return config.locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun getSystemLocale(config: Configuration): Locale {
            return config.locales.get(0)
        }

        private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}