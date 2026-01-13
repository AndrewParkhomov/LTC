package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import parkhomov.andrew.ltc.components.AppDialog

@Composable
fun TopBarInfoDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit
) {
    AppDialog(
        onDismiss = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        content = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}
