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

        // Отримуємо active window scene
        val windowScene =
            UIApplication.sharedApplication
                .connectedScenes
                .toList()
                .filterIsInstance<UIWindowScene>()
                .firstOrNull()

        // Отримуємо key window з правильним типом
        val keyWindow =
            windowScene
                ?.windows
                ?.toList()
                ?.filterIsInstance<UIWindow>()
                ?.firstOrNull { it.isKeyWindow() }

        // Показуємо share sheet
        keyWindow?.rootViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null,
        )
    }
}
