@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.toast

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Immutable
sealed interface ToastState {
    @Immutable
    data object Init : ToastState
    @Immutable
    data class Shown(
        val stringRes: StringResource,
        val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
    ) : ToastState
}
