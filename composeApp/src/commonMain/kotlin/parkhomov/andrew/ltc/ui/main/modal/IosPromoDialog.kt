package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import androidx.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.AppDialog
import parkhomov.andrew.ltc.components.DialogTextButton
import parkhomov.andrew.ltc.strings.Strings

const val APP_STORE_LINK = "https://apps.apple.com/app/lens-thickness-calculator/id6757977779"

@Preview
@Composable
fun IosPromoDialog(
    onShareClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val strings: Strings = LocalStrings.current

    AppDialog(
        onDismiss = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.iosPromoTitle,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = strings.resultShare,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        content = {
            Text(
                text = strings.iosPromoMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        },
        confirmButton = {
            DialogTextButton(
                text = strings.buttonOk,
                onClick = onDismiss
            )
        }
    )
}
