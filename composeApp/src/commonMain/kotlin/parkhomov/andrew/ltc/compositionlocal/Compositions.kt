package parkhomov.andrew.ltc.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalTopScreenToast =
    staticCompositionLocalOf<Notification> {
        noLocalProvidedFor("LocalTopScreenToast")
    }

internal val LocalToast =
    staticCompositionLocalOf<Toast> {
        noLocalProvidedFor("LocalToast")
    }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
