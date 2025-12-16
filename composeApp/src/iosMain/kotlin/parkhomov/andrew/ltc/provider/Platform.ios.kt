@file:OptIn(ExperimentalNativeApi::class, ExperimentalComposeUiApi::class)

package parkhomov.andrew.ltc.provider

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.PlatformImeOptions
import platform.UIKit.UIDevice
import platform.UIKit.UIKeyboardTypeNumbersAndPunctuation
import kotlin.experimental.ExperimentalNativeApi

class IOSPlatform : Platform {
    override val name: String =
        "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getDecimalSignedKeyboard(): KeyboardOptions {
    return KeyboardOptions.Default.copy(
//        imeAction = imeAction,
        platformImeOptions = PlatformImeOptions {
            keyboardType(UIKeyboardTypeNumbersAndPunctuation)
        }
    )
}