package parkhomov.andrew.lensthicknesscalculator.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.core.context.GlobalContext.get
import parkhomov.andrew.ltc.AppEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        get().declare<Activity>(this) // inject activity into koin for review native popup

        setContent {
            AppEntryPoint()
        }
    }
}
