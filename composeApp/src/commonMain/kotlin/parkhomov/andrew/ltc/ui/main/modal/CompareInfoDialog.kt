package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import parkhomov.andrew.ltc.strings.Strings

@Composable
fun CompareInfoDialog(onDismiss: () -> Unit) {
    val strings: Strings = LocalStrings.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        title = {
            Text(
                text = strings.infoCompareTitle,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = strings.infoCompareDesc,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(strings.buttonOk)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
