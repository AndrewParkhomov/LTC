package parkhomov.andrew.ltc

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
//import co.touchlab.crashkios.crashlytics.enableCrashlytics
//import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook

@Suppress("FunctionName")
fun MainViewController(): UIViewController {
//    enableCrashlytics()
//            setCrashlyticsUnhandledExceptionHook()
    return ComposeUIViewController {
//        LaunchedEffect(Unit) {
//            enableCrashlytics()
//            setCrashlyticsUnhandledExceptionHook()
//        }
        AppEntryPoint()
    }
}
