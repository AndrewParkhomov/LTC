package parkhomov.andrew.lensthicknesscalculator.helper

interface PreferencesHelper {
    fun getStringValue(key: String, default: String = ""): String
    fun getIntValue(key: String, default: Int = 0): Int
    fun getFloatValue(key: String, default: Float = 0f): Float
    fun getBooleanValue(key: String, default: Boolean = false): Boolean

    fun putStringValue(key: String, value: String)
    fun putIntValue(key: String, value: Int)
    fun putFloatValue(key: String, value: Float)
    fun putBooleanValue(key: String, value: Boolean = false)

}
