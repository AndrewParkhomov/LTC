@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.toast

import org.jetbrains.compose.resources.StringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed interface ToastState {
    data object Init : ToastState

    data class Shown(
        val stringRes: StringResource,
        val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ) : ToastState
}
