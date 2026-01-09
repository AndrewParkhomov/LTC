package parkhomov.andrew.ltc.toast

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.SharedFlow

@Stable
sealed interface ToastProvider {
    val toast: SharedFlow<String>
    val showTopToast: SharedFlow<ToastMessage>

    suspend fun showClassicToast(message: String)

    suspend fun showTopMessage(message: ToastMessage)
}
