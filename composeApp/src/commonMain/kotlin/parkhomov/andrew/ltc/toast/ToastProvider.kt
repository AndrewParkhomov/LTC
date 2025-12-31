package parkhomov.andrew.ltc.toast

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.StringResource

@Stable
sealed interface ToastProvider {
    val toast: SharedFlow<String>
    val showTopToast: SharedFlow<String>

    suspend fun showClassicToast(message: StringResource)

    suspend fun showClassicToast(message: String)

    suspend fun showTopMessage(message: String)
}
