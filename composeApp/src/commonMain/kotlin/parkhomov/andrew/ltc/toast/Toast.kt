package parkhomov.andrew.ltc.toast


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

const val TOAST_DURATION = 3500L

@Composable
fun AppToast(toastState: State<ToastState>) {
    val isHidden = toastState.value is ToastState.Init
    if (isHidden) return

    var isShow by remember { mutableStateOf(false) }
    val data = toastState.value as ToastState.Shown

    LaunchedEffect(data.timestamp) {
        isShow = true
        delay(TOAST_DURATION)
        isShow = false
    }

    AnimatedVisibility(
        visible = isShow,
        enter =
            slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec =
                    tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing,
                    ),
            ) + fadeIn(),
        exit =
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec =
                    tween(
                        durationMillis = 300,
                        easing = EaseOutCubic,
                    ),
            ) + fadeOut(),
    ) {
        TopMessageToast(data.stringRes)
    }
}

@Composable
private fun TopMessageToast(data: String) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier =
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 16.dp),
    ) {
        Surface(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.inverseSurface,
            shadowElevation = 8.dp,
            tonalElevation = 0.dp,
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = data,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}
