package parkhomov.andrew.ltc.analytics

interface AnalyticsService {
    fun logEvent(event: AnalyticsEvent)
}

expect fun createAnalyticsService(): AnalyticsService
