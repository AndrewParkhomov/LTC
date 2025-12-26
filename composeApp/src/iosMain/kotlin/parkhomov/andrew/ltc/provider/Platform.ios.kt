@file:OptIn(ExperimentalNativeApi::class, ExperimentalComposeUiApi::class)

package parkhomov.andrew.ltc.provider

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PlatformImeOptions
import platform.Foundation.NSBundle
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIDevice
import platform.UIKit.UIKeyboardTypeNumbersAndPunctuation
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification
import kotlin.experimental.ExperimentalNativeApi

class IOSPlatform : Platform {
    override val name: String =
        "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getVersionName(): String {
    val bundle = NSBundle.mainBundle
    val version = bundle.infoDictionary?.get("CFBundleShortVersionString") as? String
    return version ?: "Unknown"
}

actual fun getDecimalSignedKeyboard(): KeyboardOptions =
    KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next,
        platformImeOptions =
            PlatformImeOptions {
                keyboardType(UIKeyboardTypeNumbersAndPunctuation)
            },
    )

@Composable
actual fun keyboardAsState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val notificationCenter = NSNotificationCenter.defaultCenter

        val showObserver =
            notificationCenter.addObserverForName(
                name = UIKeyboardWillShowNotification,
                `object` = null,
                queue = NSOperationQueue.mainQueue,
                usingBlock = { _ ->
                    keyboardState.value = true
                },
            )

        val hideObserver =
            notificationCenter.addObserverForName(
                name = UIKeyboardWillHideNotification,
                `object` = null,
                queue = NSOperationQueue.mainQueue,
                usingBlock = { _ ->
                    keyboardState.value = false
                },
            )

        onDispose {
            notificationCenter.removeObserver(showObserver)
            notificationCenter.removeObserver(hideObserver)
        }
    }

    return keyboardState
}
