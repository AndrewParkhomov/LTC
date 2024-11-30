package parkhomov.andrew.lensthicknesscalculator.preferences

import android.content.SharedPreferences

class AppPreferencesImpl(
    private val preferences: SharedPreferences
) : AppPreferences {

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
    override fun getBooleanValue(key: String, default: Boolean): Boolean =
        getPrefsValue(key, default)

    private inline fun <reified T> getPrefsValue(key: String, default: T): T =
        when (T::class) {
            Int::class -> preferences.getInt(key, default as Int) as T
            String::class -> preferences.getString(key, default as String) as T
            Boolean::class -> preferences.getBoolean(key, default as Boolean) as T
            Float::class -> preferences.getFloat(key, default as Float) as T
            else -> default
        }
}

interface AppPreferences {
    fun getStringValue(key: String, default: String = ""): String
    fun getIntValue(key: String, default: Int = 0): Int
    fun getFloatValue(key: String, default: Float = 0f): Float
    fun getBooleanValue(key: String, default: Boolean = false): Boolean

    fun putStringValue(key: String, value: String)
    fun putIntValue(key: String, value: Int)
    fun putFloatValue(key: String, value: Float)
    fun putBooleanValue(key: String, value: Boolean = false)
}

const val APP_LANGUAGE = "SAVE_LANGUAGE_ISO2"
const val APP_THEME = "APP_THEME"