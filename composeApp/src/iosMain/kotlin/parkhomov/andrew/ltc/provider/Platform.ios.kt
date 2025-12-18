@file:OptIn(ExperimentalNativeApi::class, ExperimentalComposeUiApi::class)

package parkhomov.andrew.ltc.provider

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PlatformImeOptions
import platform.UIKit.UIDevice
import platform.UIKit.UIKeyboardTypeNumbersAndPunctuation
import kotlin.experimental.ExperimentalNativeApi
import androidx.compose.runtime.*
import kotlinx.cinterop.useContents
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.*

class IOSPlatform : Platform {
    override val name: String =
        "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getDecimalSignedKeyboard(): KeyboardOptions {
    return KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next,
        platformImeOptions = PlatformImeOptions {
            keyboardType(UIKeyboardTypeNumbersAndPunctuation)
        }
    )
}

@Composable
actual fun keyboardAsState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val notificationCenter = NSNotificationCenter.defaultCenter

        val showObserver = notificationCenter.addObserverForName(
            name = UIKeyboardWillShowNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue,
            usingBlock = { _ ->
                keyboardState.value = true
            }
        )

        val hideObserver = notificationCenter.addObserverForName(
            name = UIKeyboardWillHideNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue,
            usingBlock = { _ ->
                keyboardState.value = false
            }
        )

        onDispose {
            notificationCenter.removeObserver(showObserver)
            notificationCenter.removeObserver(hideObserver)
        }
    }

    return keyboardState
}