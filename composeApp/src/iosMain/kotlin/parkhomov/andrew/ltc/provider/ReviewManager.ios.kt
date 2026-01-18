package parkhomov.andrew.ltc.provider

import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene

actual class ReviewManager actual constructor() {
    actual fun requestReview() {
        val windowScene = UIApplication.sharedApplication.connectedScenes.firstOrNull() as? UIWindowScene
        if (windowScene != null) {
            SKStoreReviewController.requestReviewInScene(windowScene)
        }
    }
}
