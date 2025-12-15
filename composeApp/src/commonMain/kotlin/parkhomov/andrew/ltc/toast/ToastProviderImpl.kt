package parkhomov.andrew.ltc.toast

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class ToastProviderImpl : ToastProvider {
    private val _toast: MutableSharedFlow<String> = MutableSharedFlow<String>()
    override val toast: SharedFlow<String> = _toast.asSharedFlow()

    private val _showTopToast: MutableSharedFlow<StringResource> =
        MutableSharedFlow(
            replay = 1,
            extraBufferCapacity = 1,
        )
    override val showTopToast: SharedFlow<StringResource> = _showTopToast.asSharedFlow()

    override suspend fun showTopMessage(message: StringResource) {
        _showTopToast.emit(message)
    }

    override suspend fun showClassicToast(message: StringResource) {
        _toast.emit(getString(message))
    }

    override suspend fun showClassicToast(message: String) {
        _toast.emit(message)
    }
}
