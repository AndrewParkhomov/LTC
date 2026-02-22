package parkhomov.andrew.ltc.analytics

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import parkhomov.andrew.ltc.provider.getPlatform

actual fun createAnalyticsService(): AnalyticsService = AnalyticsServiceImpl()

@SuppressLint("MissingPermission")
private class AnalyticsServiceImpl :
    AnalyticsService,
    KoinComponent {
    private val context: Context by inject()
    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    override fun logEvent(event: AnalyticsEvent) {
        val bundle =
            Bundle().apply {
                putString(PARAM_PLATFORM, getPlatform().name)
                event.params.forEach { (key: String, value: Any?) ->
                    when (value) {
                        null -> Unit
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is Double -> putDouble(key, value)
                        is Float -> putFloat(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> putString(key, value.toString())
                    }
                }
            }
        firebaseAnalytics.logEvent(event.name, bundle)
    }

    companion object {
        private const val PARAM_PLATFORM = "platform"
    }
}
