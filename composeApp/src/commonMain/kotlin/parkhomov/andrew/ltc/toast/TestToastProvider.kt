package parkhomov.andrew.ltc.toast

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.jetbrains.compose.resources.StringResource

class TestToastProvider : ToastProvider {
    
    private val _toast = MutableSharedFlow<String>(replay = 0)
    override val toast: SharedFlow<String> = _toast.asSharedFlow()
    
    private val _showTopToast = MutableSharedFlow<String>(replay = 0)
    override val showTopToast: SharedFlow<String> = _showTopToast.asSharedFlow()

    val classicToastMessages = mutableListOf<String>()
    val classicToastResources = mutableListOf<StringResource>()
    val topMessages = mutableListOf<String>()

    override suspend fun showClassicToast(message: StringResource) {
        classicToastResources.add(message)
        _toast.emit(message.toString())
    }

    override suspend fun showClassicToast(message: String) {
        classicToastMessages.add(message)
        _toast.emit(message)
    }

    override suspend fun showTopMessage(message: String) {
        topMessages.add(message)
        _showTopToast.emit(message)
    }

}