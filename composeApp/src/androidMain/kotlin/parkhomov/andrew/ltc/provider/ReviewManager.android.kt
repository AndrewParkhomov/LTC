package parkhomov.andrew.ltc.provider

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class ReviewManager actual constructor() : KoinComponent {
    private val activity: Activity by inject()

    actual fun requestReview() {
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo> ->
            if (task.isSuccessful) {
                manager.launchReviewFlow(activity, task.result)
            }
        }
    }
}
