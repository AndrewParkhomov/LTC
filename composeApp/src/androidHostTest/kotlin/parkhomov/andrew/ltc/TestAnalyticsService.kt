package parkhomov.andrew.ltc

import parkhomov.andrew.ltc.analytics.AnalyticsEvent
import parkhomov.andrew.ltc.analytics.AnalyticsService

class TestAnalyticsService : AnalyticsService {
    val loggedEvents = mutableListOf<AnalyticsEvent>()

    override fun logEvent(event: AnalyticsEvent) {
        loggedEvents.add(event)
    }
}
