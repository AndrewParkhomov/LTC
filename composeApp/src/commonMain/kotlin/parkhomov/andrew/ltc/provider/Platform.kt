package parkhomov.andrew.ltc.provider

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getVersionName(): String

fun isIos() = getPlatform().name.contains("iOS")

expect fun getDecimalSignedKeyboard(): KeyboardOptions

expect fun getAlphanumericKeyboard(): KeyboardOptions

@Composable
expect fun keyboardAsState(): State<Boolean>