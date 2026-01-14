package parkhomov.andrew.ltc.provider

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

actual class ShareManager {
    actual fun shareText(
        text: String,
        title: String,
    ) {
        val activityViewController =
            UIActivityViewController(
                activityItems = listOf(text),
                applicationActivities = null,
            )

        val windowScene =
            UIApplication.sharedApplication
                .connectedScenes
                .toList()
                .filterIsInstance<UIWindowScene>()
                .firstOrNull()

        val keyWindow =
            windowScene
                ?.windows
                ?.toList()
                ?.filterIsInstance<UIWindow>()
                ?.firstOrNull { it.isKeyWindow() }

        keyWindow?.rootViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null,
        )
    }
}
