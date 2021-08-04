package parkhomov.andrew.lensthicknesscalculator.utils.prefs

import android.content.SharedPreferences
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper

class AppPreferencesHelper(private val preferences: SharedPreferences) : PreferencesHelper {

    override fun putStringValue(key: String, value: String) =
        preferences.edit().putString(key, value).apply()

    override fun putIntValue(key: String, value: Int) =
        preferences.edit().putInt(key, value).apply()

    override fun putFloatValue(key: String, value: Float) =
        preferences.edit().putFloat(key, value).apply()

    override fun putBooleanValue(key: String, value: Boolean) =
            preferences.edit().putBoolean(key, value).apply()

    override fun getStringValue(key: String, default: String): String = getPrefsValue(key, default)
    override fun getIntValue(key: String, default: Int): Int = getPrefsValue(key, default)
    override fun getFloatValue(key: String, default: Float): Float = getPrefsValue(key, default)
    override fun getBooleanValue(key: String, default: Boolean): Boolean = getPrefsValue(key, default)

    private inline fun <reified T> getPrefsValue(key: String, default: T): T =
            when (T::class) {
                Int::class -> preferences.getInt(key, default as Int) as T
                String::class -> preferences.getString(key, default as String) as T
                Boolean::class -> preferences.getBoolean(key, default as Boolean) as T
                Float::class -> preferences.getFloat(key, default as Float) as T
                else -> default
            }

    companion object {
        const val APP_LANGUAGE = "SAVE_LANGUAGE_ISO2"
    }
}