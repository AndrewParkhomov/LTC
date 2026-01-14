package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.LocalStrings
import parkhomov.andrew.ltc.components.AppDialog
import parkhomov.andrew.ltc.components.DialogTextButton

@Composable
fun DeleteConfirmDialog(
    indexName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val strings = LocalStrings.current

    AppDialog(
        onDismiss = onDismiss,
        title = {
            Text(
                text = strings.deleteIndexTitle,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        content = {
            Text(
                text = strings.deleteIndexMessage(indexName),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        dismissButton = {
            DialogTextButton(
                text = strings.buttonCancel,
                onClick = onDismiss
            )
        },
        confirmButton = {
            DialogTextButton(
                text = strings.buttonDelete,
                onClick = onConfirm,
                color = MaterialTheme.colorScheme.error
            )
        }
    )
}
