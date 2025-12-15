package parkhomov.andrew.ltc.toast

import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.StringResource

interface ToastProvider {
    val toast: SharedFlow<String>
    val showTopToast: SharedFlow<StringResource>

    suspend fun showClassicToast(message: StringResource)

    suspend fun showClassicToast(message: String)

    suspend fun showTopMessage(message: StringResource)
}
