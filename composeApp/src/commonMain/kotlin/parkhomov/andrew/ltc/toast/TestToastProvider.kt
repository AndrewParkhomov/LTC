package parkhomov.andrew.ltc.toast

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TestToastProvider : ToastProvider {

    private val _toast = MutableSharedFlow<String>(replay = 0)
    override val toast: SharedFlow<String> = _toast.asSharedFlow()

    private val _showTopToast = MutableSharedFlow<ToastMessage>(replay = 0)
    override val showTopToast: SharedFlow<ToastMessage> = _showTopToast.asSharedFlow()

    override suspend fun showClassicToast(message: String) {
        _toast.emit(message)
    }

    override suspend fun showTopMessage(message: ToastMessage) {
        _showTopToast.emit(message)
    }
}