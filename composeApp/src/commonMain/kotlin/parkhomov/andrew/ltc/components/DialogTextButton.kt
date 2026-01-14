package parkhomov.andrew.ltc.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DialogTextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    color: Color = Color.Unspecified
) {
    TextButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            color = if (color != Color.Unspecified) color else Color.Unspecified
        )
    }
}
