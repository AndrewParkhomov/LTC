package parkhomov.andrew.ltc.compositionlocal

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import parkhomov.andrew.ltc.provider.ShowToast
import parkhomov.andrew.ltc.toast.ToastState

interface LocalDependencies {
    val notification: Notification
    val toast: Toast
}

fun getDependencies(toastState: MutableState<ToastState>) =
    object : LocalDependencies {
        private val toastHelper = ShowToast()

        override val notification: Notification =
            object : Notification {
                override fun showPopUpMessage(data: String) {
                    toastState.value = ToastState.Shown(data)
                }
            }

        override val toast: Toast =
            object : Toast {

                override fun showToast(message: String) {
                    toastHelper.showNativeToast(message)
                }
            }
    }

interface Notification {
    fun showPopUpMessage(data: String)
}

interface Toast {

    fun showToast(message: String)
}
