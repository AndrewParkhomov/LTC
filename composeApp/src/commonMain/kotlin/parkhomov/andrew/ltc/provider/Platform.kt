package parkhomov.andrew.ltc.provider

import androidx.compose.foundation.text.KeyboardOptions

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun isIos() = getPlatform().name.contains("iOS")

expect fun getDecimalSignedKeyboard(): KeyboardOptions