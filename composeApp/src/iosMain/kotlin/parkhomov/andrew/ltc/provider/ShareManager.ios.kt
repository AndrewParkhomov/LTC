package parkhomov.andrew.ltc.provider

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
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

actual suspend fun StringResource.format(vararg args: Any): String {
    val rawString = getString(this)

    // Конвертуємо Java-style format в простий текст з підстановкою
    var result =
        rawString
            .replace(Regex("%\\d+\\\$s"), "%s")
            .replace(Regex("%\\d+\\\$d"), "%d")
            .replace(Regex("%\\d+\\\$f"), "%f")

    // Підставляємо аргументи по черзі
    args.forEach { arg ->
        result =
            when {
                result.contains("%s") -> result.replaceFirst("%s", arg.toString())
                result.contains("%d") -> result.replaceFirst("%d", arg.toString())
                result.contains("%f") -> result.replaceFirst("%f", arg.toString())
                else -> result
            }
    }

    return result
}
