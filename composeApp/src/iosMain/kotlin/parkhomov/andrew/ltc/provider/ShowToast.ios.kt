package parkhomov.andrew.ltc.provider

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

actual class ShowToast actual constructor() {
    actual fun showNativeToast(message: String) {
        val alert =
            UIAlertController.alertControllerWithTitle(
                title = null,
                message = message,
                preferredStyle = UIAlertControllerStyleAlert,
            )
        val rootController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootController?.presentViewController(alert, animated = true, completion = null)

        val delayInSeconds = 2.0
        val delayTime = (delayInSeconds * NSEC_PER_SEC.toDouble()).toLong()
        dispatch_after(
            dispatch_time(DISPATCH_TIME_NOW, delayTime),
            dispatch_get_main_queue(),
        ) {
            alert.dismissViewControllerAnimated(true, completion = null)
        }
    }
}
