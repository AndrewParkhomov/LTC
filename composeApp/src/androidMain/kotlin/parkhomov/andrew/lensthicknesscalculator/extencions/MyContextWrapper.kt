package parkhomov.andrew.lensthicknesscalculator.extencions


import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale

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

            val locale = when (language) {
                "po" -> Locale("pt","rBR")
                "uk" -> Locale("uk","uk_UA")
                else -> Locale.US
            }

            if (language != "" && sysLocale.language != language) {
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
                tempContext = tempContext.createConfigurationContext(config)
            }
            return MyContextWrapper(tempContext)
        }

        private fun getSystemLocaleLegacy(config: Configuration): Locale {
            return config.locale
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getSystemLocale(config: Configuration): Locale {
            return config.locales.get(0)
        }

        private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}