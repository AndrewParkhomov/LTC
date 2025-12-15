package parkhomov.andrew.ltc.provider

import android.content.Context
import android.widget.Toast
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class ShowToast actual constructor() : KoinComponent {
    private val context: Context by inject()

    actual fun showNativeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
