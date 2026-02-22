package parkhomov.andrew.ltc.analytics

import parkhomov.andrew.ltc.provider.getPlatform

actual fun createAnalyticsService(): AnalyticsService = AnalyticsServiceImpl()

private class AnalyticsServiceImpl : AnalyticsService {
    override fun logEvent(event: AnalyticsEvent) {
        val params = event.params.filterValues { it != null }.toMutableMap()
        params[PARAM_PLATFORM] = getPlatform().name
        AnalyticsHelper.logEvent(
            name = event.name,
            parameters = params,
        )
    }

    companion object {
        private const val PARAM_PLATFORM = "platform"
    }
}

object AnalyticsHelper {
    private var logEventHandler: ((String, Map<String, Any?>) -> Unit)? = null

    fun initialize(handler: (String, Map<String, Any?>) -> Unit) {
        logEventHandler = handler
    }

    fun logEvent(
        name: String,
        parameters: Map<String, Any?>,
    ) {
        logEventHandler?.invoke(name, parameters)
            ?: println("AnalyticsHelper: Firebase not initialized. Event: $name")
    }
}
