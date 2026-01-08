package parkhomov.andrew.ltc

import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(): UIViewController {
    enableCrashlytics()
    setCrashlyticsUnhandledExceptionHook()
    return ComposeUIViewController { AppEntryPoint() }
}
