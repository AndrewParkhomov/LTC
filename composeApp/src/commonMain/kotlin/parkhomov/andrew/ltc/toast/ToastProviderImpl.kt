package parkhomov.andrew.ltc.toast

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ToastProviderImpl : ToastProvider {
    private val _toast: MutableSharedFlow<String> = MutableSharedFlow<String>()
    override val toast: SharedFlow<String> = _toast.asSharedFlow()

    private val _showTopToast: MutableSharedFlow<ToastMessage> =
        MutableSharedFlow(extraBufferCapacity = 1)
    override val showTopToast: SharedFlow<ToastMessage> = _showTopToast.asSharedFlow()

    override suspend fun showTopMessage(message: ToastMessage) {
        _showTopToast.emit(message)
    }

    override suspend fun showClassicToast(message: String) {
        _toast.emit(message)
    }
}
